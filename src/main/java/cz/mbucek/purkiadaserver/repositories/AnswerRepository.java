package cz.mbucek.purkiadaserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import cz.mbucek.purkiadaserver.entities.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>, QuerydslPredicateExecutor<Answer> {

}
