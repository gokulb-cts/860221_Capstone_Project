package com.cts.capstone.fms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.service.DashboardService;
import com.cts.capstone.fms.service.EventService;

import reactor.core.publisher.Mono;

@Service
public class DashboardServiceImpl implements DashboardService{
	
	@Autowired
	private EventService eventService;

	@Override
	public Mono<Long> getTotalEvents() {
		Mono<Long> count = eventService.getEvents().count();
		return count;
	}

	@Override
	public Mono<Integer> getAllEventsTotalLivesImpacted() {
		Mono<Integer> count = eventService.getEvents().map(event -> event.getLivesImpacted()).reduce(0, (a,b) -> a+b);
		return count;
	}

	@Override
	public Mono<Integer> getAllEventsTotalVolunteers() {
		Mono<Integer> count = eventService.getEvents().map(event -> event.getTotalNoOfVolunteers()).reduce(0, (a,b) -> a+b);
		return count;
	}

	@Override
	public Mono<Integer> getAllEventsTotalParticipants() {
		Mono<Integer> count = Mono.just(0);
		return count;
	}
	
	
	
}
