package com.cts.capstone.fms.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EventDto {

	@NotNull(message = "Event Id is empty or missing")
	private String eventId;
	
	@NotNull(message = "Event Name is empty or missing")
	private String eventName;
	
	private String eventDescription;
	
	private Date eventDate;
	
	private String baseLocation;
	
	private String beneficiaryName;
	
	private String venueAddress;
	
	private String councilName;
	
	private String project;
	
	private String category;
	
	private int totalNoOfVolunteers;
	
	private double totalVolunteerHours;
	
	private double totalTravelHours;
	
	private double overallVolunteeringHours;
	
	private int livesImpacted;
	
	private int activityType;
	
	private String status;
	
	@NotNull(message = "POC User ID is missing/empty")
	private String pocUserId;
	
}
