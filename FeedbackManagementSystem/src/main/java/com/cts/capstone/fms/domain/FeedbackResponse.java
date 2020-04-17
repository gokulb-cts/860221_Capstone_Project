package com.cts.capstone.fms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "feedback_response")
@Getter
@Setter
public class FeedbackResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "event_feedback_id")
	private EventFeedback eventFeedback;

	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "question_id")
	private FeedbackQuestion question;
	
	private String answerText;
	
}
