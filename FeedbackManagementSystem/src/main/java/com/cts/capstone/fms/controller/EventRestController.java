package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.EventConstants.EVENT_END_POINT;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.capstone.fms.DTO.Event;
import com.cts.capstone.fms.service.EventService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;;

@RestController
@Slf4j
@RequestMapping("/api")
public class EventRestController {
	
	@Autowired
	public EventService eventService; 
	
	@GetMapping(value=EVENT_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Event> getAllEvents() {
		log.info("getAllEvents()");
		return eventService.getEvents();
	}
	
	@GetMapping(value = EVENT_END_POINT+"/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<Event> getEventByEventId(@PathVariable String eventId) {
		log.info("getEventByEventId()");
		return eventService.getEventByEventId(eventId);
	}
	
	@PostMapping(value = EVENT_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<Object>> addEvent(@Valid @RequestBody Event event) {
		log.info("saveEvent()" + event);
		ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
		
		return eventService.saveEvent(event)
						   .map(savedEvent -> 
				   					{
				   						log.info(savedEvent.toString());
										URI location = 
												uriBuilder
												.path("/{id}")
												.buildAndExpand(savedEvent.getEventId())
												.toUri();
										return ResponseEntity.created(location).build();
									})
						   .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
}
