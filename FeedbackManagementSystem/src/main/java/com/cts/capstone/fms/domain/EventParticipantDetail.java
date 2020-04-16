package com.cts.capstone.fms.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "event_participant_detail")
@Data
public class EventParticipantDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="event_id")
	private Event event;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="userId", referencedColumnName = "userId")
	private FmsUser participant;
	
	private int volunteerHours;
	
	private int travelHours;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name= "participation_status_id")
	private EventParticipationType participationStatus;
	
	private String businessUnit;
	
	private String iiepCategory;
	
	private byte feedbackMailSentStatus; 

}
