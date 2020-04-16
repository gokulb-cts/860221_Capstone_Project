package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.FeedbackConfigConstants.ANSWER_END_POINT;
import static com.cts.capstone.fms.constants.FeedbackConfigConstants.FEEDBACK_END_POINT;
import static com.cts.capstone.fms.constants.FeedbackConfigConstants.QUESTION_END_POINT;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.capstone.fms.domain.FeedbackAnswer;
import com.cts.capstone.fms.domain.FeedbackQuestion;
import com.cts.capstone.fms.dto.EventFeedbackDto;
import com.cts.capstone.fms.dto.FeedbackAnswerDto;
import com.cts.capstone.fms.dto.FeedbackQuestionDto;
import com.cts.capstone.fms.service.FeedbackService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class FeedbackRestController {

	@Autowired
	private FeedbackService feedbackService;

	
	//Get Feedback Questions
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = QUESTION_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<FeedbackQuestion> getFeedbackQuestions(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		log.info("getFeedbackQuestions()");
		if (page > 0)
			page -= 1;
		return feedbackService.getFeedbackQuestions(page, limit);

	}

	
	//Get Feedback Question by ID
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = QUESTION_END_POINT + "/{questionId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<FeedbackQuestion>> getFeedbackQuestionById(@PathVariable Long questionId) {

		log.info("getFeedbackQuestionById()");
		return feedbackService.getFeedbackQuestionByQuestionId(questionId)
				.map(question -> new ResponseEntity<FeedbackQuestion>(question, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<FeedbackQuestion>(HttpStatus.NOT_FOUND));

	}

	
	//Add New Question
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = QUESTION_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<Object>> addQuestion(@Valid @RequestBody FeedbackQuestionDto questionDto) {
		log.info("addQuestion()" + questionDto);

		ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

		return feedbackService.saveFeedbackQuestion(questionDto).map(savedQuestion -> {
			URI location = uriBuilder.path("/{id}").buildAndExpand(savedQuestion.getId()).toUri();
			return ResponseEntity.created(location).build();
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	
	//Update Question
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = QUESTION_END_POINT + "/{questionId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<FeedbackQuestion>> updateFeedbackQuestion(@PathVariable Long questionId,
			@RequestBody FeedbackQuestionDto feedQuestionDto) {

		log.info("updateFeedbackQuestion()");

		return feedbackService.updateFeedbackQuestion(questionId, feedQuestionDto)
				.map(updatedQuestion -> new ResponseEntity<>(updatedQuestion, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	
	//Delete Question
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = QUESTION_END_POINT + "/{questionId}")
	public void deleteFeedbackQuestion(@PathVariable Long questionId) {

		log.info("deleteFeedbackQuestion()");
		feedbackService.deleteFeedbackQuestion(questionId);

	}

	
	//Delete Answer
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = ANSWER_END_POINT + "/{questionId}")
	public void deleteFeedbackAnswer(@PathVariable Long answerId) {

		log.info("deleteFeedbackAnswer()");
		feedbackService.deleteFeedbackAnswer(answerId);

	}
	
	
	//Update Answer
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = ANSWER_END_POINT + "/{answerId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<FeedbackAnswer>> updateFeedbackAnswer(@PathVariable Long answerId,
			@RequestBody FeedbackAnswerDto feedbackAnswerDto) {

		log.info("updateFeedbackAnswer()");

		return feedbackService.updateFeedbackAnswer(answerId, feedbackAnswerDto)
				.map(updatedAnswer -> new ResponseEntity<>(updatedAnswer, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	
	//Save Feedback From Participant
	@PostMapping(value = FEEDBACK_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<Object>> saveFeedback(@Valid @RequestBody EventFeedbackDto eventFeedbackDto) {
		log.info("saveFeedback()" + eventFeedbackDto);

		ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

		return feedbackService.saveFeedback(eventFeedbackDto).map(savedFeedback -> {
			URI location = uriBuilder.path("/{id}").buildAndExpand(savedFeedback.getId()).toUri();
			return ResponseEntity.created(location).build();
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}
	
	
}
