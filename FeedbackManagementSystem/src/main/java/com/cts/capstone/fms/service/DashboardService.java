package com.cts.capstone.fms.service;

import reactor.core.publisher.Mono;

public interface DashboardService {

	public Mono<Long> getTotalEvents();
	
	public Mono<Integer> getAllEventsTotalLivesImpacted();
	
	public Mono<Integer> getAllEventsTotalVolunteers();
	
	public Mono<Integer> getAllEventsTotalParticipants();

	public Mono<Integer> getTotalEventsByPocId(Long pocId);

	public Mono<Integer> getEventTotalLivesImpactedByPocId(Long pocId);

	public Mono<Integer> getEventTotalVolunteersByPocId(Long pocId);

	public Mono<Integer> getEventTotalParticipantsByPocId(Long pocId);
	
}
