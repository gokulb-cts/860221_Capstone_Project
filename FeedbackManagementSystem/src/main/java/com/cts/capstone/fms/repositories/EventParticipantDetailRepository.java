package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.Event;
import com.cts.capstone.fms.domain.EventParticipantDetail;
import com.cts.capstone.fms.domain.FmsUser;

@Repository
public interface EventParticipantDetailRepository extends JpaRepository<EventParticipantDetail, Long>{
	
	public EventParticipantDetail findByEventAndParticipant(Event event, FmsUser participant);
	
}
