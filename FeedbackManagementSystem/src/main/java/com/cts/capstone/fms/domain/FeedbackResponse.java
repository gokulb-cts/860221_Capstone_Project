package com.cts.capstone.fms.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "feedback_response")
@Entity
@Data
public class FeedbackResponse {
	@Id
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "question_id")
	private FeedbackQuestion question;
	
	private String feedbackAnswer;
	
}
