package com.cts.capstone.fms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "event_feedback")
@Getter
@Setter
public class EventFeedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private FmsUser user;

	@OneToOne
	@JoinColumn(name= "participation_status_id")
	private EventParticipationType participationStatus;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "eventFeedback", cascade = CascadeType.ALL)
	private List<FeedbackResponse> feedbackResponse;

	private Integer rating;
	
	private Date createdDate;
	
}
