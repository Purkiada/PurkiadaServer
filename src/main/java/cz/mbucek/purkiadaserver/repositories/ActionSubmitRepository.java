package cz.mbucek.purkiadaserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import cz.mbucek.purkiadaserver.entities.ActionSubmit;

public interface ActionSubmitRepository extends JpaRepository<ActionSubmit, Long>, QuerydslPredicateExecutor<ActionSubmit>{

}
