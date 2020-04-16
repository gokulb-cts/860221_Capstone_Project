package com.cts.capstone.fms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.cts.capstone.fms.dto.EventReportDto;

public interface EventReportService {

	ByteArrayInputStream generateEventReportWorkbook(List<EventReportDto> eventReportDtoList) throws IOException ;

	void sendEventReportViaMail(Long userId, List<EventReportDto> eventReportDtoList) throws MessagingException, IOException  ;

}
