package cz.mbucek.purkiadaserver.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import cz.mbucek.purkiadaserver.dtos.TaskDTO;
import cz.mbucek.purkiadaserver.entities.QTask;
import cz.mbucek.purkiadaserver.entities.Task;
import cz.mbucek.purkiadaserver.entities.Tasklist;
import cz.mbucek.purkiadaserver.repositories.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	public List<Task> getTasksByTasklist(Tasklist tasklist, Pageable pageable) {
		Predicate predicate = QTask.task.tasklist.eq(tasklist);
		return taskRepository.findAll(predicate, pageable).toList();
	}
	
	public Optional<Task> getTaskByTasklistAndId(Tasklist tasklist, long id){
		Predicate predicate = QTask.task.tasklist.eq(tasklist).and(QTask.task.id.eq(id));
		return taskRepository.findOne(predicate);
	}
	
	public Task createTask(Tasklist tasklist, TaskDTO task) {
		return null;
	}
	
	public Task deleteTask(Task task) {
		taskRepository.delete(task);
		return task;
	}
}
