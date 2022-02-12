package cz.mbucek.purkiadaserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.mbucek.purkiadaserver.entities.Task;
import cz.mbucek.purkiadaserver.services.TaskService;

@RestController
@RequestMapping("/{actionId}/tasklist/{tasklistId}/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@GetMapping("")
	public List<Task> getTasksByTasklist(){
		return null;
	}
	
	@GetMapping("/{taskId}")
	public Task getTaskByTasklistAndId() {
		return null;
	}
	
	@PutMapping("")
	public Task createTask() {
		return null;
	}
	
	@DeleteMapping("/{taskId}")
	public Task deleteTask() {
		return null;
	}
}
