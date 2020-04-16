package com.cts.capstone.fms.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.domain.Event;
import com.cts.capstone.fms.domain.EventParticipantDetail;
import com.cts.capstone.fms.domain.EventParticipationType;
import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.dto.EventParticipantDetailDto;
import com.cts.capstone.fms.enums.RoleEnum;
import com.cts.capstone.fms.exception.FmsApplicationException;
import com.cts.capstone.fms.repositories.EventParticipantDetailRepository;
import com.cts.capstone.fms.repositories.EventParticipationTypeRepository;
import com.cts.capstone.fms.repositories.EventRepository;
import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.repositories.RoleRepository;
import com.cts.capstone.fms.service.EventParticipantDetailService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class EventParticipantDetailServiceImpl implements EventParticipantDetailService {


	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private FmsUserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EventParticipantDetailRepository eventParticipantDetailRepository;
	

	@Autowired
	private EventParticipationTypeRepository eventParticipationTypeRepository;
	
	@Override
	public Flux<EventParticipantDetail> getAllEventParticipantDetails() {
		return Flux.fromIterable(eventParticipantDetailRepository.findAll());
	}

	@Override
	public Mono<EventParticipantDetail> saveEventParticipantDetails(EventParticipantDetailDto eventParticipantDetailDto) throws FmsApplicationException {
		
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		EventParticipantDetail eventParticipantDetail  = modelMapper.map(eventParticipantDetailDto, EventParticipantDetail.class);
		
		//Get Event By Event ID
		Event event = eventRepository.findByEventId(eventParticipantDetailDto.getEventId());
		
		if(event == null) throw new FmsApplicationException("Event not found for Event ID: " + eventParticipantDetailDto.getEventId());
		
		
		//Get Employee By User ID
		FmsUser participant = userRepository.findByUserId(eventParticipantDetailDto.getUserId());
		
		if(participant == null) {
			participant = new FmsUser();
			participant.setUserId(eventParticipantDetailDto.getUserId());
			participant.setUserName(eventParticipantDetailDto.getUserName());
			
			Role role = roleRepository.findByRoleNameIgnoreCase(RoleEnum.ROLE_PARTICIPANT.toString());
			participant.setRole(role);
		}
		
		//Get Participation Status By Name
		EventParticipationType eventParticipationType = eventParticipationTypeRepository.findByNameIgnoreCase(eventParticipantDetailDto.getParticipationStatus());
		if(eventParticipationType == null) {
			eventParticipationType = new EventParticipationType();
			eventParticipationType.setName(eventParticipantDetailDto.getParticipationStatus());
			eventParticipationType.setDescription(eventParticipantDetailDto.getParticipationStatus());
		}
		
		eventParticipantDetail.setEvent(event);
		eventParticipantDetail.setParticipant(participant);
		eventParticipantDetail.setParticipationStatus(eventParticipationType);
		
		return Mono.just(eventParticipantDetailRepository.save(eventParticipantDetail));
		
	}

}
