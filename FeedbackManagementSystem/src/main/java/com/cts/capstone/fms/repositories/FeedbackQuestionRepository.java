package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.FeedbackQuestion;

@Repository
public interface FeedbackQuestionRepository extends JpaRepository<FeedbackQuestion, Long>{

}
