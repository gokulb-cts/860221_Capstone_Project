package com.cts.capstone.fms.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "event_participant_detail")
@Entity
@Data
public class EventParticipantDetail {
	
	@Id
	private Long id;
	
	@OneToOne
	@JoinColumn(name="event_id")
	private Event event;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private FmsUser participant;
	
	@OneToOne
	@JoinColumn(name= "participation_status_id")
	private EventParticipationType participationStatus;
	
	private byte feedbackMailSentStatus; 

}
