package com.cts.capstone.fms.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Table(name = "feedback_answer")
@Entity
@Data
@JsonIgnoreProperties(value = {"createdBy","createdDate","modifiedBy","lastModifiedDate"}, allowSetters = true)
public class FeedbackAnswer {
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private FeedbackQuestion feedbackQuestion;
	
	@NotNull(message = "Answer Text is missing/empty")
	private String answerText;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long modifiedBy;
	
	private Date lastModifiedDate; 

}
