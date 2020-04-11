package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.capstone.fms.domain.EventFeedback;

public interface EventFeedbackRepository extends JpaRepository<EventFeedback, Long>{

}
