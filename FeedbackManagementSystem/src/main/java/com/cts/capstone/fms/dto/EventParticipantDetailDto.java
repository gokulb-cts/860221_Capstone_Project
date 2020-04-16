package com.cts.capstone.fms.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EventParticipantDetailDto {
	
		private Long id;
		
		@NotNull(message = "Event ID is empty/missing")
		private String eventId;
		
		@NotNull(message = "Employee ID is empty/missing")
		private Long userId;
		
		@NotNull(message = "Employee Name is empty/missing")
		private String userName;
		
		private int volunteerHours;
		
		private int travelHours;
		
		@NotNull(message = "Participation Status is empty/missing")
		private String participationStatus;
		
		private String businessUnit;
		
		private String iiepCategory;
		
		private byte feedbackMailSentStatus; 

}
