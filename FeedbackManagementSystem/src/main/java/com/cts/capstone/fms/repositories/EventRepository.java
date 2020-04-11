package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	public Event findByEventId(String eventId);
}
