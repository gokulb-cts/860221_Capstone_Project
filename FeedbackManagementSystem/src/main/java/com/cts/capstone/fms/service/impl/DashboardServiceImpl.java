package com.cts.capstone.fms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.service.DashboardService;
import com.cts.capstone.fms.service.EventService;
import com.cts.capstone.fms.service.FmsUserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DashboardServiceImpl implements DashboardService{
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private FmsUserService userService;
	
	@Override
	public Mono<Long> getTotalEvents() {
		Mono<Long> count = eventService.getAllEvents().count();
		return count;
	}

	@Override
	public Mono<Integer> getAllEventsTotalLivesImpacted() {
		Mono<Integer> count = eventService.getAllEvents().map(event -> event.getLivesImpacted()).reduce(0, (a,b) -> a+b);
		return count;
	}

	@Override
	public Mono<Integer> getAllEventsTotalVolunteers() {
		Mono<Integer> count = eventService.getAllEvents().map(event -> event.getTotalNoOfVolunteers()).reduce(0, (a,b) -> a+b);
		return count;
	}

	@Override
	public Mono<Integer> getAllEventsTotalParticipants() {
		Mono<Integer> count = eventService.getAllEvents().map(event -> event.getTotalNoOfVolunteers()).reduce(0, (a,b) -> a+b);
		return count;
	}

	@Override
	public Mono<Integer> getTotalEventsByPocId(Long pocId) {
		return userService.getUserByUserId(pocId)
					.flatMap(user -> Mono.just(user.getEvents().size()))
					.defaultIfEmpty(0);
	}

	@Override
	public Mono<Integer> getEventTotalLivesImpactedByPocId(Long pocId) {
		return userService.getUserByUserId(pocId)
						.flatMapMany(user -> Flux.fromIterable(user.getEvents()))
						.map(event -> event.getLivesImpacted())
						.reduce(0, (a,b) -> a+b);
	}

	@Override
	public Mono<Integer> getEventTotalVolunteersByPocId(Long pocId) {
		return userService.getUserByUserId(pocId)
				.flatMapMany(user -> Flux.fromIterable(user.getEvents()))
				.map(event -> event.getTotalNoOfVolunteers())
				.reduce(0, (a,b) -> a+b);
	}

	@Override
	public Mono<Integer> getEventTotalParticipantsByPocId(Long pocId) {
		return userService.getUserByUserId(pocId)
				.flatMapMany(user -> Flux.fromIterable(user.getEvents()))
				.map(event -> event.getTotalNoOfVolunteers())
				.reduce(0, (a,b) -> a+b);
	}
	
	
	
}
