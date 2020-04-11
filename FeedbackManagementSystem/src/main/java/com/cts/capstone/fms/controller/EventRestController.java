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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.capstone.fms.domain.Event;
import com.cts.capstone.fms.dto.EventDto;
import com.cts.capstone.fms.service.EventService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class EventRestController {

	@Autowired
	private EventService eventService;

	@GetMapping(value = EVENT_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Event> getAllEvents(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		log.info("getAllEvents()");
		if (page > 0)
			page -= 1;
		return eventService.getEvents(page, limit);
	}

	@GetMapping(value = EVENT_END_POINT + "/{eventId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<Event>> getEventByEventId(@PathVariable String eventId) {
		log.info("getEventByEventId()");
		return eventService.getEventByEventId(eventId).map(event -> new ResponseEntity<Event>(event, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(value = EVENT_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<Object>> addEvent(@Valid @RequestBody EventDto eventDto) {
		log.info("saveEvent()" + eventDto);

		ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

		return eventService.saveEvent(eventDto).map(savedEvent -> {
			URI location = uriBuilder.path("/{id}").buildAndExpand(savedEvent.getEventId()).toUri();
			return ResponseEntity.created(location).build();
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

}
