package com.cts.capstone.fms.dto;

import java.util.List;

import lombok.Data;

@Data
public class EventFeedbackRequestDto {

	private String eventId;
	
	private Long userId;
	
	private Long participationTypeId;
	
	private String participationType;
	
	private List<ParticipantFeedbackQuestionDto> feedbackQuestions;
	
}
