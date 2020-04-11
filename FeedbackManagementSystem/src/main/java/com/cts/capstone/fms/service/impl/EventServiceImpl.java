package com.cts.capstone.fms.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.domain.Event;
import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.dto.EventDto;
import com.cts.capstone.fms.enums.Authority;
import com.cts.capstone.fms.repositories.EventRepository;
import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.repositories.RoleRepository;
import com.cts.capstone.fms.service.EventService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	FmsUserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Mono<Event> saveEvent(EventDto eventDto) {
		Event event  = new ModelMapper().map(eventDto, Event.class);
		
		Set<FmsUser> users = this.getUsers(eventDto.getPocUserIds());
		
		//Add Poc role to Poc users
		for(FmsUser user : users) {
			Role userRole = user.getRole();
			if(userRole == null || 
					(userRole != null && !userRole.getRoleName().equalsIgnoreCase(Authority.POC.toString()))) {
				userRole = roleRepository.findByRoleNameIgnoreCase(Authority.POC.toString());
			}
			user.setRole(userRole);
		}
		event.setPocUser(users);
		return Mono.just(eventRepository.save(event));
	}

	private Set<FmsUser> getUsers(Set<String> userIds) {
		Set<FmsUser> users = new HashSet<FmsUser>();
		for(String userIdStr : userIds) {
			Long userId = Long.parseLong(userIdStr);
			FmsUser user = userRepository.findByUserId(userId);
			if(user == null) {
				user = new FmsUser();
				user.setUserId(userId);
				user.setUserName(userIdStr);
			}
			users.add(user);
		}
		return users;
	}

	@Override
	public Flux<Event> getEvents(int page, int limit) {
		Pageable pageableRequest = PageRequest.of(page, limit);
		return Flux.fromIterable(eventRepository.findAll(pageableRequest).getContent());
	}

	@Override
	public Flux<Event> getAllEvents() {
		return Flux.fromIterable(eventRepository.findAll());
	}
	
	@Override
	public Mono<Event> getEventByEventId(String eventId) {
		return Mono.just(eventRepository.findByEventId(eventId));
	}
	
}
