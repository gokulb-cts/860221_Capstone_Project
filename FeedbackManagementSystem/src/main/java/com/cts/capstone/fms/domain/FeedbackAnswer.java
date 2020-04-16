package com.cts.capstone.fms.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "feedback_answer")
@Getter
@Setter
@JsonIgnoreProperties(value = {"createdBy","createdDate","modifiedBy","lastModifiedDate"}, allowSetters = true)
public class FeedbackAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	@JsonBackReference
	private FeedbackQuestion feedbackQuestion;
	
	private String answerText;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long modifiedBy;
	
	private Date lastModifiedDate; 

}
