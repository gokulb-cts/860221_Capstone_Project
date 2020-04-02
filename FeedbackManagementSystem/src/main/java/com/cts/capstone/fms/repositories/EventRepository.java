package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.capstone.fms.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	public Event findByEventId(String eventId);
}
