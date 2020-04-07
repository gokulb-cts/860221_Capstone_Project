package com.cts.capstone.fms.service;

import com.cts.capstone.fms.domain.Event;
import com.cts.capstone.fms.dto.EventDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
	
	public Mono<Event> saveEvent(EventDto eventDto);
	
	public Flux<Event> getEvents();
	
	public Mono<Event> getEventByEventId(String eventId);

}
