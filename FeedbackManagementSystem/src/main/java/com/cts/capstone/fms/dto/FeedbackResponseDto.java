package com.cts.capstone.fms.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class FeedbackResponseDto {

	private Long id;
	
	@NotNull(message = "Question ID is empty/missing")
	private Long questionId;
	
	@NotNull(message = "Answer Text is empty/missing")
	@JsonIgnore
	private String answerText;
	
}
