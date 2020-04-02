package com.cts.capstone.fms.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.DTO.Event;

import reactor.core.publisher.Mono;

@Repository
public interface EventReactiveRepository extends ReactiveCrudRepository<Event,String>{
	
	public Mono<Event> findByEventId(String eventId);
	
}
