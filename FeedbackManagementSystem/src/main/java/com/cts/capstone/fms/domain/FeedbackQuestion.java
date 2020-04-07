package com.cts.capstone.fms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Table(name = "feedback_question")
@Entity
@Data
@JsonIgnoreProperties(value = {"createdBy","createdDate","modifiedBy","lastModifiedDate"}, allowSetters = true)
public class FeedbackQuestion {
	@Id
	private Long id;
	@NotNull(message = "Question Text is missing/empty")
	private String questionText;
	
	private String feedbackType;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long modifiedBy;
	
	private Date lastModifiedDate; 
	
	@OneToOne
	@JoinColumn(name = "participationTypeId")
	private EventParticipationType eventParticipationType;
	
	@OneToMany
	private List<FeedbackAnswer> feedbackAnswers;	

}
