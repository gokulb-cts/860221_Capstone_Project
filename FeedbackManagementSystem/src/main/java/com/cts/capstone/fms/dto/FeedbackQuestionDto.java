package com.cts.capstone.fms.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FeedbackQuestionDto {

	private Long id;
	
	@NotNull(message = "Question Text is missing/empty")
	private String questionText;
	
	private String feedbackType;
	
	private String participationStatusType;
	
	private List<FeedbackAnswerDto> answers;	
	
}
