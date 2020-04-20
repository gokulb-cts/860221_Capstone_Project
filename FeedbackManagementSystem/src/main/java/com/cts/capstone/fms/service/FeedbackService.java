package com.cts.capstone.fms.service;

import com.cts.capstone.fms.domain.EventFeedback;
import com.cts.capstone.fms.domain.EventParticipationType;
import com.cts.capstone.fms.domain.FeedbackAnswer;
import com.cts.capstone.fms.domain.FeedbackQuestion;
import com.cts.capstone.fms.dto.EventFeedbackDto;
import com.cts.capstone.fms.dto.EventFeedbackRequestDto;
import com.cts.capstone.fms.dto.FeedbackAnswerDto;
import com.cts.capstone.fms.dto.FeedbackQuestionDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FeedbackService {

	public Flux<FeedbackQuestion> getFeedbackQuestions(int page, int limit);

	public Mono<FeedbackQuestion> getFeedbackQuestionByQuestionId(Long questionId);
	
	public Flux<FeedbackQuestion> getFeedbackQuestionByParticipationType(EventParticipationType participationType);

	public Mono<FeedbackQuestion> saveFeedbackQuestion(FeedbackQuestionDto questionDto);

	public void deleteFeedbackQuestion(Long questionId);

	public Mono<FeedbackQuestion> updateFeedbackQuestion(Long questionId, FeedbackQuestionDto feedQuestionDto);

	public void deleteFeedbackAnswer(Long answerId);

	public Mono<FeedbackAnswer> updateFeedbackAnswer(Long answerId, FeedbackAnswerDto feedbackAnswerDto);

	public Mono<EventFeedbackRequestDto> getEventFeedbackRequestForParticipant(String eventId, Long userId);
	
	public Mono<EventFeedback> saveFeedback(EventFeedbackDto eventFeedbackDto);

}
