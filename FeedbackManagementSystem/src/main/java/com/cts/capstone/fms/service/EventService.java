package com.cts.capstone.fms.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.cts.capstone.fms.DTO.Event;

public interface EventService {
	
	public Mono<Event> saveEvent(Event event);
	
	public Flux<Event> getEvents();
	
	public Mono<Event> getEventByEventId(String eventId);

}
