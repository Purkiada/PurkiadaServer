package cz.mbucek.purkiadaserver.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import cz.mbucek.purkiadaserver.utilities.exceptions.InternalServerErrorException;

@Service
public class ActionService {
	@Autowired
	private ActionRepository actionRepository;
	@Autowired
	private ActionSubmitRepository actionSubmitRepository;

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
				actionDTO.hidden()
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
}
