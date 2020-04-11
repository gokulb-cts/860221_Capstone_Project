package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.EventParticipationType;

@Repository
public interface EventParticipationTypeRepository extends JpaRepository<EventParticipationType, Long>{
	
	public EventParticipationType findByNameIgnoreCase(String name);
	
}
