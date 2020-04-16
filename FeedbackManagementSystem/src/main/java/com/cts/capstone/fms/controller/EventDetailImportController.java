package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.EventDetailImportConstants.EVENT_DETAIL_IMPORT_END_POINT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.capstone.fms.exception.FmsApplicationException;
import com.cts.capstone.fms.service.EventNonParticipantsImportService;
import com.cts.capstone.fms.service.EventParticipantsImportService;
import com.cts.capstone.fms.service.EventParticipantsUnRegisteredImportService;
import com.cts.capstone.fms.service.EventsImportService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class EventDetailImportController {

	@Autowired
	private EventsImportService eventsImportService;
	
	@Autowired
	private EventParticipantsImportService eventParticipantsImportService;
	
	@Autowired
	private EventNonParticipantsImportService eventNonParticipantsService;
	
	@Autowired
	private EventParticipantsUnRegisteredImportService eventParticipantsUnRegisteredService;
	
	
	//Import Event Detail Excel File Data to DB
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = EVENT_DETAIL_IMPORT_END_POINT + "/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<String> importAllEventDetails() throws IOException, FmsApplicationException {
		
		log.info("import Event and Participants data from File to DB");
		
		
		eventsImportService.importEventsFromFileLocal();
		
		eventParticipantsImportService.importEventParticipantsFromFileLocal();
		
		eventNonParticipantsService.importEventNonParticipantsFromFileLocal();
		
		eventParticipantsUnRegisteredService.importEventParticipantsUnregisteredFromFileLocal();
		
		return Mono.just("success");
		
	}
	
}
