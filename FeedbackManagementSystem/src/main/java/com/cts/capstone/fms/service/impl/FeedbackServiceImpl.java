package com.cts.capstone.fms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cts.capstone.fms.domain.Event;
import com.cts.capstone.fms.domain.EventFeedback;
import com.cts.capstone.fms.domain.EventParticipantDetail;
import com.cts.capstone.fms.domain.EventParticipationType;
import com.cts.capstone.fms.domain.FeedbackAnswer;
import com.cts.capstone.fms.domain.FeedbackQuestion;
import com.cts.capstone.fms.domain.FeedbackResponse;
import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.dto.EventFeedbackDto;
import com.cts.capstone.fms.dto.EventFeedbackRequestDto;
import com.cts.capstone.fms.dto.FeedbackAnswerDto;
import com.cts.capstone.fms.dto.FeedbackQuestionDto;
import com.cts.capstone.fms.dto.ParticipantFeedbackAnswerDto;
import com.cts.capstone.fms.dto.ParticipantFeedbackQuestionDto;
import com.cts.capstone.fms.repositories.EventFeedbackRepository;
import com.cts.capstone.fms.repositories.EventParticipantDetailRepository;
import com.cts.capstone.fms.repositories.EventParticipationTypeRepository;
import com.cts.capstone.fms.repositories.EventRepository;
import com.cts.capstone.fms.repositories.FeedbackAnswerRepository;
import com.cts.capstone.fms.repositories.FeedbackQuestionRepository;
import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.service.FeedbackService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private FmsUserRepository userRepository;

	@Autowired
	private FeedbackQuestionRepository feedbackQuestionRepository;

	@Autowired
	private FeedbackAnswerRepository feedbackAnswerRepository;

	@Autowired
	private EventParticipationTypeRepository participationStatusTypeRepository;

	@Autowired
	private EventFeedbackRepository eventFeedbackRepository;

	@Autowired
	private EventParticipantDetailRepository eventParticipantDetailRepository;

	@Override
	public Flux<FeedbackQuestion> getFeedbackQuestions(int page, int limit) {
		Pageable pageableRequest = PageRequest.of(page, limit);
		return Flux.fromIterable(feedbackQuestionRepository.findAll(pageableRequest).getContent());
	}

	@Override
	public Mono<FeedbackQuestion> getFeedbackQuestionByQuestionId(Long questionId) {
		return Mono.just(feedbackQuestionRepository.findById(questionId).get());
	}

	@Override
	public Mono<FeedbackQuestion> saveFeedbackQuestion(FeedbackQuestionDto questionDto) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		FeedbackQuestion question = modelMapper.map(questionDto, FeedbackQuestion.class);

		// Set Participation Status
		if (!StringUtils.isEmpty(questionDto.getParticipationStatusType())) {
			EventParticipationType participationStatusType = participationStatusTypeRepository
					.findByNameIgnoreCase(questionDto.getParticipationStatusType());
			if (participationStatusType == null) {

				participationStatusType = new EventParticipationType();
				participationStatusType.setName(questionDto.getParticipationStatusType());
				participationStatusType.setDescription(questionDto.getParticipationStatusType());

			}
			question.setEventParticipationType(participationStatusType);
		}

		// Add Answers
		if (!questionDto.getAnswers().isEmpty()) {
			List<FeedbackAnswer> feedbackAnswers = questionDto.getAnswers().stream().map(answerDto -> {
				FeedbackAnswer answer = modelMapper.map(answerDto, FeedbackAnswer.class);
				answer.setFeedbackQuestion(question);
				return answer;
			}).collect(Collectors.toList());

			question.setAnswerList(feedbackAnswers);
		}

		return Mono.justOrEmpty(feedbackQuestionRepository.save(question));
	}

	@Override
	public void deleteFeedbackQuestion(Long questionId) {

		Optional<FeedbackQuestion> question = feedbackQuestionRepository.findById(questionId);

		if (!question.isPresent()) {
			throw new RuntimeException("Question does not exist with ID: " + questionId);
		}

		feedbackQuestionRepository.deleteById(questionId);
	}

	@Override
	public Mono<FeedbackQuestion> updateFeedbackQuestion(Long questionId, FeedbackQuestionDto questionDto) {

		Optional<FeedbackQuestion> question = feedbackQuestionRepository.findById(questionId);

		if (question.isPresent()) {
			FeedbackQuestion feedbackQuestion = question.get();
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull())
					.setMatchingStrategy(MatchingStrategies.STRICT);
			modelMapper.map(questionDto, feedbackQuestion);

			// Set Event Participation Type
			if (questionDto.getParticipationStatusType() != null && !question.get().getEventParticipationType()
					.getName().equalsIgnoreCase(questionDto.getParticipationStatusType())) {

				EventParticipationType participationStatusType = participationStatusTypeRepository
						.findByNameIgnoreCase(questionDto.getParticipationStatusType());
				if (participationStatusType == null) {

					participationStatusType = new EventParticipationType();
					participationStatusType.setName(questionDto.getParticipationStatusType());
					participationStatusType.setDescription(questionDto.getParticipationStatusType());

				}
				feedbackQuestion.setEventParticipationType(participationStatusType);

			}

			// Set Answers
			if (questionDto.getAnswers() != null) {
				List<FeedbackAnswer> feedbackAnswers = questionDto.getAnswers().stream().map(answerDto -> {
					FeedbackAnswer answer = modelMapper.map(answerDto, FeedbackAnswer.class);
					answer.setFeedbackQuestion(feedbackQuestion);
					return answer;
				}).collect(Collectors.toList());
				feedbackQuestion.setAnswerList(feedbackAnswers);
			}

			return Mono.just(feedbackQuestionRepository.save(feedbackQuestion));
		}

		return Mono.empty();

	}

	@Override
	public Mono<FeedbackAnswer> updateFeedbackAnswer(Long answerId, FeedbackAnswerDto feedbackAnswerDto) {

		Optional<FeedbackAnswer> answerOp = feedbackAnswerRepository.findById(answerId);

		if (answerOp.isPresent()) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			FeedbackAnswer answer = answerOp.get();
			answer = modelMapper.map(feedbackAnswerDto, FeedbackAnswer.class);
			return Mono.just(feedbackAnswerRepository.save(answer));
		}
		return Mono.empty();

	}

	@Override
	public void deleteFeedbackAnswer(Long answerId) {
		Optional<FeedbackAnswer> answer = feedbackAnswerRepository.findById(answerId);

		if (!answer.isPresent()) {
			throw new RuntimeException("Answer does not exist with ID: " + answerId);
		}

		feedbackQuestionRepository.deleteById(answerId);

	}

	@Override
	public Flux<FeedbackQuestion> getFeedbackQuestionByParticipationType(EventParticipationType participationType) {

		return Flux.fromIterable(feedbackQuestionRepository.findByEventParticipationType(participationType));

	}

	@Override
	public Mono<EventFeedbackRequestDto> getEventFeedbackRequestForParticipant(String eventId, Long userId) {
		
		//Get user
		FmsUser participant = userRepository.findByUserId(userId);
		if (participant == null) {
			throw new RuntimeException("Participant not found with ID : " + userId);
		}
		
		//Get event 
		Event event = eventRepository.findByEventId(eventId);
		if (event == null) {
			throw new RuntimeException("Event not found with Event ID : " + eventId);
		}
		
		//Get Event Participant Detail
		EventParticipantDetail eventParticipantDetail = eventParticipantDetailRepository.findByEventAndParticipant(event, participant);
		if (eventParticipantDetail == null) {
			throw new RuntimeException("Participant did not register for this event");
		}
		
		//Participant Status
		EventParticipationType participationStatus = eventParticipantDetail.getParticipationStatus();
		
		if(participationStatus == null) {
			throw new RuntimeException("No Questions found against the participation type");
		}
		
		//Get Participation Type Feedback Questions(participated, unregistered etc) 
		List<FeedbackQuestion> feedbackQuestions =  feedbackQuestionRepository
					.findByEventParticipationType(participationStatus);
		
		List<ParticipantFeedbackQuestionDto> participantFeedbackQuestions = feedbackQuestions.stream()
																	.map(question -> 
																	{ 
																		List<ParticipantFeedbackAnswerDto> feedbackAnswers =
																				question.getAnswerList().stream()
																					.map(answer -> new ParticipantFeedbackAnswerDto(answer.getAnswerText()))
																					.collect(Collectors.toList());
																		
																		return new ParticipantFeedbackQuestionDto(question.getId(),
																												question.getQuestionText(),
																												feedbackAnswers);
																	})
																	.collect(Collectors.toList());
		
		EventFeedbackRequestDto eventFeedbackRequestDto = new EventFeedbackRequestDto();
		
		eventFeedbackRequestDto.setEventId(eventId);
		eventFeedbackRequestDto.setUserId(userId);
		eventFeedbackRequestDto.setParticipationTypeId(participationStatus.getId());
		eventFeedbackRequestDto.setParticipationType(participationStatus.getName());
		eventFeedbackRequestDto.setFeedbackQuestions(participantFeedbackQuestions);
		
		return Mono.justOrEmpty(eventFeedbackRequestDto);
	}

	@Override
	public Mono<EventFeedback> saveFeedback(EventFeedbackDto eventFeedbackDto) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		EventFeedback eventFeedback = modelMapper.map(eventFeedbackDto, EventFeedback.class);

		// Set Event
		if (!StringUtils.isEmpty(eventFeedbackDto.getEventId())) {
			Event event = eventRepository.findByEventId(eventFeedbackDto.getEventId());
			if (event == null) {
				throw new RuntimeException("Event not found with Event ID : " + eventFeedbackDto.getEventId());
			}
			eventFeedback.setEvent(event);
		}

		// Set User
		if (eventFeedbackDto.getUserId() != null && eventFeedbackDto.getUserId() != 0) {
			FmsUser user = userRepository.findByUserId(eventFeedbackDto.getUserId());
			if (user == null) {
				throw new RuntimeException("User not found with User ID : " + eventFeedbackDto.getUserId());
			}
			eventFeedback.setUser(user);
		}

		// Set Participation Type
		if (eventFeedbackDto.getParticipationTypeId() != null && eventFeedbackDto.getParticipationTypeId() != 0) {
			Optional<EventParticipationType> participationTypeOp = participationStatusTypeRepository
					.findById(eventFeedbackDto.getParticipationTypeId());
			if (!participationTypeOp.isPresent()) {
				throw new RuntimeException("Participation Status not found with Participation ID : "
						+ eventFeedbackDto.getParticipationTypeId());
			}
			eventFeedback.setParticipationStatus(participationTypeOp.get());
		}

		// Add Feedback Response
		if (!eventFeedbackDto.getFeedback().isEmpty()) {
			List<FeedbackResponse> feedbackResponses = eventFeedbackDto.getFeedback().stream().map(feedbackResDto -> {
				FeedbackResponse feedbackResponse = modelMapper.map(feedbackResDto, FeedbackResponse.class);
				Optional<FeedbackQuestion> feedbackQuestion = feedbackQuestionRepository
						.findById(feedbackResDto.getQuestionId());
				if (!feedbackQuestion.isPresent())
					throw new RuntimeException(
							"Question with Question ID not found : " + feedbackResDto.getQuestionId());
				feedbackResponse.setQuestion(feedbackQuestion.get());
				feedbackResponse.setEventFeedback(eventFeedback);
				return feedbackResponse;
			}).collect(Collectors.toList());

			eventFeedback.setFeedback(feedbackResponses);
		}

		Mono<EventFeedback> result = Mono.justOrEmpty(eventFeedbackRepository.save(eventFeedback));

		EventParticipantDetail eventParticipantDetail = eventParticipantDetailRepository
				.findByEventAndParticipant(eventFeedback.getEvent(), eventFeedback.getUser());

		eventParticipantDetail.setFeedbackSubmittedStatus((byte) 1);
		eventParticipantDetailRepository.save(eventParticipantDetail);

		return result;
	}

}
