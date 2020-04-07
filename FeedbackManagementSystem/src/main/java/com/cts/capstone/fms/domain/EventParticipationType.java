package com.cts.capstone.fms.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Table(name = "event_participation_type")
@Entity
@Data
public class EventParticipationType {
	@Id
	private Long id;
	
	@NotNull(message = "Participation Type Name is missing/empty")
	private String name;

	private String description;

}
