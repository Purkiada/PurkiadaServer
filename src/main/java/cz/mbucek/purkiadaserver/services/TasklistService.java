package cz.mbucek.purkiadaserver.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.querydsl.core.types.Predicate;

import cz.mbucek.purkiadaserver.dtos.TasklistDTO;
import cz.mbucek.purkiadaserver.entities.Action;
import cz.mbucek.purkiadaserver.entities.QTasklist;
import cz.mbucek.purkiadaserver.entities.Tasklist;
import cz.mbucek.purkiadaserver.repositories.TasklistRepository;

@Service
public class TasklistService {

	@Autowired
	private TasklistRepository tasklistRepository;
	
	public List<Tasklist> getTasklistsByAction(Action action, Pageable pageable){
		Predicate predicate = QTasklist.tasklist.action.eq(action);
		return tasklistRepository.findAll(predicate, pageable).toList();
	}
	
	public Optional<Tasklist> getTasklistByActionAndId(Action action, Long id) {
		Predicate predicate = QTasklist.tasklist.action.eq(action).and(QTasklist.tasklist.id.eq(id));
		return tasklistRepository.findOne(predicate);
	}
	
	public Tasklist createTasklist(Action action, TasklistDTO tasklistDTO) {
		var tasklist = new Tasklist(action, HtmlUtils.htmlEscape(tasklistDTO.name(), "UTF-8"));
		return tasklistRepository.save(tasklist);
	}

	public Tasklist deleteTasklist(Tasklist tasklist) {
		tasklistRepository.delete(tasklist);
		return tasklist;
	}
}
