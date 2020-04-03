package com.cts.capstone.fms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Event Id is empty or missing")
	private String eventId;
	private String eventName;
	private String eventDescription;
	private Date eventDate;
	private String baseLocation;
	private String beneficiaryName;
	private String venueAddress;
	private String councilName;
	private String project;
	private String category;
	private int totalNoOfVolunteers;
	private double totalVolunteerHours;
	private double totalTravelHours;
	private double overallVolunteeringHours;
	private int livesImpacted;
	private int activityType;
	private String status;
	
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "poc_id", referencedColumnName = "userId")
	private FmsUser pocUser;
}
