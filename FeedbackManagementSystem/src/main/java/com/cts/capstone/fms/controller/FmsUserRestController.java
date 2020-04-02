package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.EventConstants.EVENT_END_POINT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.capstone.fms.DTO.Event;
import com.cts.capstone.fms.service.FmsUserService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping("/api")
public class FmsUserRestController {
	
	@Autowired
	public FmsUserService fmsUserService; 
	
	@GetMapping(value=EVENT_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Event> getAllEvents() {
		log.info("getAllEvents()");
		return fmsUserService.getEvents();
	}
}
