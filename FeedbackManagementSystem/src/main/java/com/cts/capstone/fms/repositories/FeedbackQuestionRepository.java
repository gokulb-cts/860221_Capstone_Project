package com.cts.capstone.fms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.EventParticipationType;
import com.cts.capstone.fms.domain.FeedbackQuestion;

@Repository
public interface FeedbackQuestionRepository extends JpaRepository<FeedbackQuestion, Long>{

	public List<FeedbackQuestion> findByEventParticipationType(EventParticipationType participationType);
	
}
