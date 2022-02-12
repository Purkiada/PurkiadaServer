package cz.mbucek.purkiadaserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import cz.mbucek.purkiadaserver.entities.Tasklist;

public interface TasklistRepository extends JpaRepository<Tasklist, Long>, QuerydslPredicateExecutor<Tasklist> {

}
