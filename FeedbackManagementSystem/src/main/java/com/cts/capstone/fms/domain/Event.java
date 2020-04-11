package com.cts.capstone.fms.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String eventId;
	
	@Column(nullable = false, unique = true)
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
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "event_poc_user", joinColumns = @JoinColumn(name = "eventId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	private Set<FmsUser> pocUser;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "event")
	private List<EventFeedback> eventFeedbackList;

}
