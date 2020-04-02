package com.cts.capstone.fms.service;

import com.cts.capstone.fms.domain.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
	
	public Mono<Event> saveEvent(Event event);
	
	public Flux<Event> getEvents();
	
	public Mono<Event> getEventByEventId(String eventId);

}
