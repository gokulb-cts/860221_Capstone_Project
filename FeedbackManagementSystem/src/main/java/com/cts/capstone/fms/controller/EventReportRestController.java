package com.cts.capstone.fms.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.capstone.fms.constants.EventReportConstants;
import com.cts.capstone.fms.dto.EventReportDto;
import com.cts.capstone.fms.service.EventReportService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class EventReportRestController {

	@Autowired
	EventReportService eventReportService;
	
	//Generate Excel Report for download
	//@PreAuthorize("hasAnyRole('ADMIN','PMO')")
	@PostMapping(value = EventReportConstants.EVENTS_REPORT_END_POINT + "/download", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<InputStreamResource>> downloadEventReport(@RequestBody List<EventReportDto> eventReportDtoList) throws IOException {
		
		log.info("downloadEventReport()");
		ByteArrayInputStream in = eventReportService.generateEventReportWorkbook(eventReportDtoList);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Event-Report.xlsx");
    
        return Mono.just(new ResponseEntity<InputStreamResource>(new InputStreamResource(in),headers,HttpStatus.OK));
	}
	
	//Send Report as Email
	@PostMapping(value = EventReportConstants.EVENTS_REPORT_END_POINT + "/mail/{userId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<String> sendEventReportViaMail(@PathVariable Long userId, @RequestBody List<EventReportDto> eventReportDtoList) throws IOException, MessagingException {
		
		log.info("sendEventReportToMail()");
		
		eventReportService.sendEventReportViaMail(userId, eventReportDtoList);
		
		return Mono.just("Mail Sent");
	}
	
}
