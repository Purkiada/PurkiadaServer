package cz.mbucek.purkiadaserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import cz.mbucek.purkiadaserver.entities.Action;

public interface ActionRepository extends JpaRepository<Action, Long>, QuerydslPredicateExecutor<Action>{
	
}
