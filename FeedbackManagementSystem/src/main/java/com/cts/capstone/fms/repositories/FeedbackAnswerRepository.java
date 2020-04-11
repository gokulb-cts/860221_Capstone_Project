package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.FeedbackAnswer;

@Repository
public interface FeedbackAnswerRepository extends JpaRepository<FeedbackAnswer, Long>{
	
}
