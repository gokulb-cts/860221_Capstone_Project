package com.cts.capstone.fms.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EventReportDto {

	private String eventId;
	
	private String eventName;
	
	private String month;
	
	private String eventDescription;
	
	private Date eventDate;
	
	private String baseLocation;
	
	private String beneficiaryName;
	
	private String councilName;
	
	private String project;
	
	private String category;
	
	private int totalNoOfVolunteers;
	
	private double totalVolunteerHours;
	
	private double totalTravelHours;
	
	private double overallVolunteeringHours;
	
	private int livesImpacted;
	
	private double averageRating;
	
	
}
