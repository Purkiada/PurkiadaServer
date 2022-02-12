package cz.mbucek.purkiadaserver.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.querydsl.core.types.Predicate;

import cz.mbucek.purkiadaserver.dtos.ActionDTO;
import cz.mbucek.purkiadaserver.entities.Action;
import cz.mbucek.purkiadaserver.entities.ActionSubmit;
import cz.mbucek.purkiadaserver.entities.QAction;
import cz.mbucek.purkiadaserver.entities.QActionSubmit;
import cz.mbucek.purkiadaserver.entities.User;
import cz.mbucek.purkiadaserver.entities.enums.ActionStatus;
import cz.mbucek.purkiadaserver.repositories.ActionRepository;
import cz.mbucek.purkiadaserver.repositories.ActionSubmitRepository;
import cz.mbucek.purkiadaserver.utilities.HashUtils;
import cz.mbucek.purkiadaserver.utilities.Pair;
import cz.mbucek.purkiadaserver.utilities.exceptions.Asserts;
import cz.mbucek.purkiadaserver.utilities.exceptions.InternalServerErrorException;

@Service
public class ActionService {

	@Autowired
	private ActionRepository actionRepository;

	@Autowired
	private ActionSubmitRepository actionSubmitRepository;

	@Autowired
	private AuthService authService;

	public Action createAction(ActionDTO actionDTO) {
		Action action = new Action(
				HtmlUtils.htmlEscape(actionDTO.name(), "UTF-8"),
				HtmlUtils.htmlEscape(actionDTO.subName(), "UTF-8"),
				HtmlUtils.htmlEscape(actionDTO.description(), "UTF-8"),
				actionDTO.registrationStart(),
				actionDTO.registrationEnd(),
				actionDTO.actionStart(),
				actionDTO.actionEnd(),
				actionDTO.maxUsers(),
				actionDTO.hidden(),
				actionDTO.authenticationType()
				);
		return actionRepository.save(action);
	}

	public Action updateAction(Action action, ActionDTO actionDTO) {
		if(actionDTO.name() != null)
			action.setName(HtmlUtils.htmlEscape(actionDTO.name(), "UTF-8"));
		if(actionDTO.subName() != null)
			action.setSubName(HtmlUtils.htmlEscape(actionDTO.subName(), "UTF-8"));
		if(actionDTO.description() != null)
			action.setDescription(HtmlUtils.htmlEscape(actionDTO.description(), "UTF-8"));
		if(actionDTO.registrationStart() != null)
			action.setRegistrationStart(actionDTO.registrationStart());
		if(actionDTO.registrationEnd() != null)
			action.setRegistrationEnd(actionDTO.registrationEnd());
		if(actionDTO.actionStart() != null)
			action.setActionStart(actionDTO.actionStart());
		if(actionDTO.actionEnd() != null)
			action.setActionEnd(actionDTO.actionEnd());
		if(actionDTO.maxUsers() != null)
			action.setMaxUsers(actionDTO.maxUsers());
		if(actionDTO.hidden() != null)
			action.setHidden(actionDTO.hidden());
		if(actionDTO.authenticationType() != null)
			action.setAuthenticationType(actionDTO.authenticationType());
		return actionRepository.save(action);
	}

	public List<Action> getActions(Pageable pageable) {
		return actionRepository.findAll();
	}

	public List<Action> getPublicActions(Pageable pageable){
		Predicate predicate = QAction.action.hidden.eq(false);
		return actionRepository.findAll(predicate, pageable).toList();
	}

	public List<Action> getActionsByUser(Pageable pageable, User user){
		return (user.hasRole("admin"))? getActions(pageable) : getPublicActions(pageable);
	}

	public Set<Action> getRunningActions(){
		return null;
	}

	public Optional<Action> getActionById(long actionId) {
		Predicate predicate = QAction.action.id.eq(actionId);
		return actionRepository.findOne(predicate);
	}
	

	public Optional<Action> getActionByIdAndPublic(long actionId) {
		Predicate predicate = QAction.action.id.eq(actionId).and(QAction.action.hidden.eq(false));
		return actionRepository.findOne(predicate);
	}

	public ActionSubmit submitForAction(User user, Action action) {
		var submitted = isUserSubmittedForAction(user, action);
		if(submitted.isEmpty()) {
			if(!hasActionFreeSpace(action)) {
				throw new InternalServerErrorException();
			}
			if(action.getStatus() != ActionStatus.REGISTRATION_IN_PROGRESS) {
				throw new InternalServerErrorException();
			}
			ActionSubmit submit = new ActionSubmit(action, user);
			actionSubmitRepository.save(submit);
			return submit;
		}
		return submitted.get();
	}

	public boolean hasActionFreeSpace(Action action) {
		Predicate predicate = QActionSubmit.actionSubmit.action.eq(action);
		return action.getMaxUsers() > actionSubmitRepository.count(predicate);	
	}

	public List<ActionSubmit> getSubmittedUsersForAction(Action action, Pageable pageable){
		Predicate predicate = QActionSubmit.actionSubmit.action.eq(action);
		return actionSubmitRepository.findAll(predicate, pageable).toList();
	}

	public Optional<ActionSubmit> isUserSubmittedForAction(User user, Action action) {
		Predicate predicate = QActionSubmit.actionSubmit.user.eq(user).and(QActionSubmit.actionSubmit.action.eq(action));
		return actionSubmitRepository.findOne(predicate);
	}

	public Action deleteAction(Action action) {
		actionRepository.delete(action);
		return action;
	}

	public Optional<Action> getActionByIdAndUser(Long actionId, User user) {
		return (user.hasRole("admin"))? getActionById(actionId) : getActionByIdAndPublic(actionId);
	}

	public List<ActionSubmit> getSubmitsByAction(Action action){
		var submits = action.getSubmits();
		submits.forEach(submit -> {
			submit.setPublicUser(authService.getUserById(submit.getUser().getUserId()));
		});
		return submits.stream().toList();
	}

	public Optional<ActionSubmit> getActionSubmitByActionAndId(Action action, Long submitId) {
		Predicate predicate = QActionSubmit.actionSubmit.action.eq(action).and(QActionSubmit.actionSubmit.id.eq(submitId));
		return actionSubmitRepository.findOne(predicate);
	}
	
	public ActionSubmit deleteActionSubmit(ActionSubmit submit) {
		Asserts.notNull(submit, new InternalServerErrorException());
		actionSubmitRepository.delete(submit);
		return submit;
	}

	public ActionSubmit regenerateAccessToken(ActionSubmit actionSubmit) {
		actionSubmit.setLegacyAccessToken(HashUtils.generateRandomPassword(10));
		actionSubmitRepository.save(actionSubmit);
		return actionSubmit;
	}
	
	public byte[] exportActionToExcel(Action action) throws IOException {
		var random = new Random();
		var workbook = new XSSFWorkbook();
		var informationSheet = workbook.createSheet("Informations");
		var userSheet = workbook.createSheet("Users");
		
		var submits = getSubmitsByAction(action);
		
		var informations = List.of(
				new Pair<String, Object>("ID", action.getId()),
				new Pair<String, Object>("Name", action.getName()),
				new Pair<String, Object>("SubName", action.getSubName()),
				new Pair<String, Object>("Description", action.getDescription()),
				new Pair<String, Object>("Registration start", action.getRegistrationStart()),
				new Pair<String, Object>("Registration end", action.getRegistrationEnd()),
				new Pair<String, Object>("Action start", action.getActionStart()),
				new Pair<String, Object>("Action end", action.getActionEnd()),
				new Pair<String, Object>("Maximal number of users", action.getMaxUsers()),
				new Pair<String, Object>("Hidden", action.getHidden()),
				new Pair<String, Object>("Authentication type", action.getAuthenticationType())
				);
		
		for(int i = 0; i < informations.size(); i++) {
			var pair = informations.get(i);
			var row = informationSheet.createRow(i);
			var cell = row.createCell(0);
			cell.setCellValue(pair.key());
			cell = row.createCell(1);
			cell.setCellValue(pair.value().toString());
		}
		
		var header = userSheet.createRow(0);
		var headerCell = header.createCell(0);
		headerCell.setCellValue("ID");
		headerCell = header.createCell(1);
		headerCell.setCellValue("First name");
		headerCell = header.createCell(2);
		headerCell.setCellValue("Last name");
		headerCell = header.createCell(3);
		headerCell.setCellValue("Email");
		headerCell = header.createCell(4);
		headerCell.setCellValue("Username");
		headerCell = header.createCell(5);
		headerCell.setCellValue("Password");
		
		for(int i = 1; i < submits.size() + 1; i++) {
			var submit = submits.get(i - 1);
			var user = submit.getPublicUser();
			var row = userSheet.createRow(i);
			var cell = row.createCell(0);
			cell.setCellValue(user.id());
			cell = row.createCell(1);
			cell.setCellValue(user.firstname());
			cell = row.createCell(2);
			cell.setCellValue(user.lastname());
			cell = row.createCell(3);
			cell.setCellValue(user.email());
			cell = row.createCell(4);
			var username = user.firstname().substring(0, 2).toUpperCase() + user.lastname().substring(0, 2).toUpperCase() + random.nextInt(10, 20);
			cell.setCellValue(Normalizer.normalize(username, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
			cell = row.createCell(5);
			cell.setCellValue(submit.getLegacyAccessToken());
		}
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		workbook.close();
		return os.toByteArray();
	}
}
