package com.cts.capstone.fms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "event_feedback")
@Entity
@Data
public class EventFeedback {
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private FmsUser user;
	
	@OneToMany
	@JoinColumn(name = "feedback_response_id")
	private List<FeedbackResponse> feedbackResponseList;

	private Integer rating;
	
	private Date createdDate;
	
}
