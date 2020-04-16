package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.EventFeedback;

@Repository
public interface EventFeedbackRepository extends JpaRepository<EventFeedback, Long>{

}
