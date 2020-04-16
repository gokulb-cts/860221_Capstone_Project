package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.DashboardConstants.DASHBOARD_END_POINT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.capstone.fms.dto.Dashboard;
import com.cts.capstone.fms.security.UserPrincipal;
import com.cts.capstone.fms.service.DashboardService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class DashboardRestController {
	
	@Autowired
	private DashboardService dashboardService;
	
	//Get Dashboard data for all events
	@PreAuthorize("hasAnyRole('ADMIN','PMO')")
	@GetMapping(value = DASHBOARD_END_POINT + "/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Dashboard> getDashboardDataAll() {
		log.info("getDashBoardData()");
		List<Dashboard> dashBoardList = new ArrayList<>();
		
		Dashboard dashboard = new Dashboard();
		//get Total Events
		dashboardService.getTotalEvents()
						.subscribe(totalEvents -> dashboard.setTotalEvents(totalEvents));
		//get Lives Impacted
		dashboardService.getAllEventsTotalLivesImpacted()
						.subscribe(totalLivesImpacted -> dashboard.setLiveImpacted(totalLivesImpacted));
		
		//get Total Volunteers
		dashboardService.getAllEventsTotalVolunteers()
						.subscribe(totalVolunteers -> dashboard.setTotalVolunteers(totalVolunteers));
		
		//get Total Participants
		dashboardService.getAllEventsTotalParticipants()
						.subscribe(totalParticipants -> dashboard.setTotalParticipants(totalParticipants));
		
		dashBoardList.add(dashboard);
		
		return Flux.fromIterable(dashBoardList);
	}

	
	//Get Dashboard for POC 
	@GetMapping(value = DASHBOARD_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Dashboard> getDashboardData(Authentication auth) {
		log.info("getDashBoardData()");

		String userId = ((UserPrincipal) auth.getPrincipal()).getUsername();
		
		if(StringUtils.isEmpty(userId)) return Flux.empty();
		
		List<Dashboard> dashBoardList = new ArrayList<>();
		
		Dashboard dashboard = new Dashboard();
		//get Total Events
		dashboardService.getTotalEventsByPocId(Long.parseLong(userId))
						.subscribe(totalEvents -> dashboard.setTotalEvents(totalEvents));
		//get Lives Impacted
		dashboardService.getEventTotalLivesImpactedByPocId(Long.parseLong(userId))
						.subscribe(totalLivesImpacted -> dashboard.setLiveImpacted(totalLivesImpacted));
		
		//get Total Volunteers
		dashboardService.getEventTotalVolunteersByPocId(Long.parseLong(userId))
						.subscribe(totalVolunteers -> dashboard.setTotalVolunteers(totalVolunteers));
		
		//get Total Participants
		dashboardService.getEventTotalParticipantsByPocId(Long.parseLong(userId))
						.subscribe(totalParticipants -> dashboard.setTotalParticipants(totalParticipants));
		
		dashBoardList.add(dashboard);
		
		return Flux.fromIterable(dashBoardList);
	}

	
}
