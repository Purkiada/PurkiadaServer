package cz.mbucek.purkiadaserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import cz.mbucek.purkiadaserver.dtos.ActionDTO;
import cz.mbucek.purkiadaserver.entities.Action;
import cz.mbucek.purkiadaserver.entities.ActionSubmit;
import cz.mbucek.purkiadaserver.entities.User;
import cz.mbucek.purkiadaserver.services.ActionService;
import cz.mbucek.purkiadaserver.utilities.MappingUtils;
import cz.mbucek.purkiadaserver.utilities.View.Extended;
import cz.mbucek.purkiadaserver.utilities.View.Public;
import cz.mbucek.purkiadaserver.utilities.exceptions.Asserts;
import cz.mbucek.purkiadaserver.utilities.exceptions.InternalServerErrorException;
import cz.mbucek.purkiadaserver.utilities.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/v1/action")
public class ActionController {

	@Autowired
	private ActionService actionService;

	@Autowired
	private User user;

	@GetMapping
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",  
							description = "Returns all actions that can specific user see.", 
							content = @Content(
									mediaType = "application/json",
									array = @ArraySchema(
											schema = @Schema(implementation = Action.class)
											)
									)
							)
			}
			)
	public MappingJacksonValue getActions(Pageable pageable){
		return MappingUtils.basicMappingForUser(actionService.getActionsByUser(pageable, user), user);
	}

	@GetMapping("/{actionId}")
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",  
							description = "Returns an action based on ID and which specific user can see.", 
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(
											implementation = Action.class
											)
									)
							)
			}
			)
	public MappingJacksonValue getAction(
			@PathVariable Long actionId
			) {
		var action = actionService.getActionByIdAndPublic(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		return MappingUtils.basicMappingForUser(action.get(), user);
	}

	@PutMapping("")
	@JsonView(Extended.class)
	public Action createAction(
			@Valid @RequestBody ActionDTO action
			) {
		return actionService.createAction(action);
	}

	@PatchMapping("/{actionId}")
	@JsonView(Extended.class)
	public Action updateAction(
			@PathVariable Long actionId,
			@Valid @RequestBody ActionDTO actionDTO	
			) {
		var action = actionService.getActionById(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		return actionService.updateAction(action.get(), actionDTO);
	}

	@DeleteMapping("/{actionId}")
	@JsonView(Extended.class)
	public Action deleteAction(
			@PathVariable Long actionId
			) {
		var action = actionService.getActionById(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		return actionService.deleteAction(action.get());
	}
	
	@GetMapping("/{actionId}/export")
	@JsonView(Extended.class)
	public ResponseEntity<byte[]> exportAction(
			@PathVariable Long actionId
			) {
		var action = actionService.getActionById(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		var headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM);
		headers.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(action.get().getId() + "_export.xlsx").build().toString());
		try {
			return ResponseEntity.ok().headers(headers).body(actionService.exportActionToExcel(action.get()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new InternalServerErrorException();
	}

	@GetMapping("/{actionId}/submitted")
	@JsonView(Public.class)
	public ActionSubmit isSubmittedForAction(
			@PathVariable Long actionId
			) {
		var action = actionService.getActionByIdAndUser(actionId, user);
		Asserts.notEmpty(action, new NotFoundException());
		var submitted = actionService.isUserSubmittedForAction(user, action.get());
		Asserts.notEmpty(submitted, new NotFoundException());
		return submitted.get();
	}

	@GetMapping("/{actionId}/submit")
	@JsonView(Extended.class)
	public List<ActionSubmit> getActionSubmits(
			@PathVariable Long actionId
			){
		var action = actionService.getActionById(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		return actionService.getSubmitsByAction(action.get());
	}

	@DeleteMapping("/{actionId}/submit/{submitId}")
	@JsonView(Extended.class)
	public ActionSubmit deleteActionSubmit(
			@PathVariable Long actionId,
			@PathVariable Long submitId
			) {
		var action = actionService.getActionById(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		var submit = actionService.getActionSubmitByActionAndId(action.get(), submitId);
		Asserts.notEmpty(submit, new NotFoundException());
		return actionService.deleteActionSubmit(submit.get());
	}

	@PutMapping("/{actionId}/submit")
	@JsonView(Public.class)
	public MappingJacksonValue submitForAction(
			@PathVariable Long actionId
			) {
		var action = actionService.getActionByIdAndUser(actionId, user);
		Asserts.notEmpty(action, new NotFoundException());
		return MappingUtils.basicMappingForUser(actionService.submitForAction(user, action.get()), user);
	}

	@PostMapping("/{actionId}/submit/{submitId}/regenerateAccessToken")
	@JsonView(Extended.class)
	public ActionSubmit regenerateAccessToken(
			@PathVariable Long actionId,
			@PathVariable Long submitId
			) {
		var action = actionService.getActionById(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		var submit = actionService.getActionSubmitByActionAndId(action.get(), submitId);
		Asserts.notEmpty(submit, new NotFoundException());
		return actionService.regenerateAccessToken(submit.get());
	}
}
