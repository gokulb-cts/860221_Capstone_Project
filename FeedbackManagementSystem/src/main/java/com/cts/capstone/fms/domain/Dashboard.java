package com.cts.capstone.fms.domain;


import lombok.Data;

@Data
public class Dashboard {
	private long totalEvents;
	private int liveImpacted;
	private int totalVolunteers;
	private int totalParticipants;
}
