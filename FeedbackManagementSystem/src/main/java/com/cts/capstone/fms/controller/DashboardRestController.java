package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.DashboardConstants.DASHBOARD_END_POINT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.capstone.fms.dto.Dashboard;
import com.cts.capstone.fms.service.DashboardService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class DashboardRestController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping(value = DASHBOARD_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Dashboard> getDashboardData() {
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
	
}
