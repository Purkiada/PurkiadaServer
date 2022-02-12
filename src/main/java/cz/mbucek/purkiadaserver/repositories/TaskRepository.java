package cz.mbucek.purkiadaserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import cz.mbucek.purkiadaserver.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long>, QuerydslPredicateExecutor<Task> {

}
