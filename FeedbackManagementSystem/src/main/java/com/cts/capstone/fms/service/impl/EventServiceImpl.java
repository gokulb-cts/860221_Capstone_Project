package com.cts.capstone.fms.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.domain.Event;
import com.cts.capstone.fms.dto.EventDto;
import com.cts.capstone.fms.repositories.EventRepository;
import com.cts.capstone.fms.service.EventService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;
	
	@Override
	public Mono<Event> saveEvent(EventDto eventDto) {
		Event event  = new Event();
		BeanUtils.copyProperties(eventDto, event);
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
