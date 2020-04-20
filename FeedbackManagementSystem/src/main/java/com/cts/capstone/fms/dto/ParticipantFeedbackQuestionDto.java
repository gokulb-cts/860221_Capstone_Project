package com.cts.capstone.fms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantFeedbackQuestionDto {

	private Long questionId;
	
	private String question;
	
	private List<ParticipantFeedbackAnswerDto> feedbackAnswers;
	
}
