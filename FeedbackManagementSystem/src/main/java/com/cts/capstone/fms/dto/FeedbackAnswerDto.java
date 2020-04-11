package com.cts.capstone.fms.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FeedbackAnswerDto {
	
	private Long id;
	
	@NotNull(message = "Answer Text is missing/empty")
	private String answerText;

}
