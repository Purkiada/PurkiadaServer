package cz.mbucek.purkiadaserver.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.mbucek.purkiadaserver.dtos.TasklistDTO;
import cz.mbucek.purkiadaserver.entities.Tasklist;
import cz.mbucek.purkiadaserver.entities.User;
import cz.mbucek.purkiadaserver.services.ActionService;
import cz.mbucek.purkiadaserver.services.TasklistService;
import cz.mbucek.purkiadaserver.utilities.exceptions.Asserts;
import cz.mbucek.purkiadaserver.utilities.exceptions.NotFoundException;

@RestController
@RequestMapping("/{actionId}/tasklist")
public class TasklistController {

	@Autowired
	private ActionService actionService;
	
	@Autowired
	private TasklistService tasklistService;
	
	@Autowired
	private User user;

	@GetMapping("")
	public List<Tasklist> getTasklistsByAction(
			@PathVariable Long actionId,
			Pageable pageable
			) {
		var action = actionService.getActionByIdAndUser(actionId, user);
		Asserts.notEmpty(action, new NotFoundException());
		return tasklistService.getTasklistsByAction(action.get(), pageable);
	}
	
	@GetMapping("/{tasklistId}")
	public Tasklist getTasklistByActionAndId(
			@PathVariable Long actionId,
			@PathVariable Long tasklistId
			) {
		var action = actionService.getActionByIdAndUser(actionId, user);
		Asserts.notEmpty(action, new NotFoundException());
		var tasklist = tasklistService.getTasklistByActionAndId(action.get(), tasklistId);
		Asserts.notEmpty(tasklist, new NotFoundException());
		return tasklist.get();
	}
	
	@PutMapping("")
	public Tasklist createTasklist(
			@PathVariable Long actionId,
			@Valid TasklistDTO tasklistDTO
			) {
		var action = actionService.getActionById(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		return tasklistService.createTasklist(action.get(), tasklistDTO);
	}
	
	@DeleteMapping("/{tasklistId}")
	public Tasklist deleteTasklist(
			@PathVariable Long actionId,
			@PathVariable Long tasklistId
			) {
		var action = actionService.getActionById(actionId);
		Asserts.notEmpty(action, new NotFoundException());
		var tasklist = tasklistService.getTasklistByActionAndId(action.get(), tasklistId);
		Asserts.notEmpty(tasklist, new NotFoundException());
		return tasklistService.deleteTasklist(tasklist.get());
	}
}
