package com.cts.capstone.fms.dto;


import lombok.Data;

@Data
public class Dashboard {
	private long totalEvents;
	private int liveImpacted;
	private int totalVolunteers;
	private int totalParticipants;
}
