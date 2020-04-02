package com.cts.capstone.fms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.cts.capstone.fms.domain.Event;
import com.cts.capstone.fms.repositories.EventRepository;
import com.cts.capstone.fms.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;
	
	@Override
	public Mono<Event> saveEvent(Event event) {
		return Mono.just(eventRepository.save(event));
	}

	@Override
	public Flux<Event> getEvents() {
		return Flux.fromIterable(eventRepository.findAll());
	}

	@Override
	public Mono<Event> getEventByEventId(String eventId) {
		return Mono.just(eventRepository.findByEventId(eventId));
	}
	
}
