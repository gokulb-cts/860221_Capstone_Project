package com.cts.capstone.fms.service;

import com.cts.capstone.fms.domain.EventParticipantDetail;
import com.cts.capstone.fms.dto.EventParticipantDetailDto;
import com.cts.capstone.fms.exception.FmsApplicationException;
import com.cts.capstone.fms.exception.UserNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventParticipantDetailService {

	public Flux<EventParticipantDetail> getAllEventParticipantDetails();
	
	public Mono<EventParticipantDetail> saveEventParticipantDetails(EventParticipantDetailDto eventParticipantDetailDto) throws FmsApplicationException;
	
}
