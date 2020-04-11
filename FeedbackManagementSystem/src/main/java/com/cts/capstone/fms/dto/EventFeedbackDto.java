package com.cts.capstone.fms.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EventFeedbackDto {
	
	private Long id;
	
	@NotNull(message = "Event ID is empty/missing")
	private String eventId;
	
	@NotNull(message = "User ID is empty/missing")
	private Long userId;
	
	private List<FeedbackResponseDto> feedbackResponse;

	private Integer rating;

}
