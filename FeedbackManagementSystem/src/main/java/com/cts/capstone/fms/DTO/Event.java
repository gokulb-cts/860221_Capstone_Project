package com.cts.capstone.fms.DTO;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
public class Event {
	@Id
	@GeneratedValue
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
	private double total_volunteer_hours;
	private double total_travel_hours;
	private double overall_volunteering_hours;
	private int livesImpacted;
	private int activityType;
	private String status;
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "pocId", referencedColumnName = "userId")
	private FmsUser fmsUser;
}
