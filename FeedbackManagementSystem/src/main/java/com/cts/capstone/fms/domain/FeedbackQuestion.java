package com.cts.capstone.fms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "feedback_question")
@Data
@JsonIgnoreProperties(value = {"createdBy","createdDate","modifiedBy","lastModifiedDate"}, allowSetters = true)
public class FeedbackQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String questionText;
	
	private String feedbackType;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long modifiedBy;
	
	private Date lastModifiedDate; 
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH ,CascadeType.REFRESH})
	@JoinColumn(name = "participationTypeId")
	private EventParticipationType eventParticipationType;
	
	@OneToMany(mappedBy = "feedbackQuestion", cascade = CascadeType.ALL)
	@JsonManagedReference
 	private List<FeedbackAnswer> answers;	

}
