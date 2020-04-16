package com.cts.capstone.fms.service.impl;

import static com.cts.capstone.fms.util.ExcelUtils.getCellValueAsString;
import static com.cts.capstone.fms.util.ExcelUtils.getHeaderColumnNames;
import static com.cts.capstone.fms.util.ExcelUtils.isCellEmpty;
import static com.cts.capstone.fms.util.ExcelUtils.isRowEmpty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.constants.EventDetailImportConstants;
import com.cts.capstone.fms.dto.EventParticipantDetailDto;
import com.cts.capstone.fms.exception.FmsApplicationException;
import com.cts.capstone.fms.service.EventParticipantDetailService;
import com.cts.capstone.fms.service.EventParticipantsUnRegisteredImportService;
import com.cts.capstone.fms.util.ExcelUtils;

@Service
public class EventParticipantsUnRegisteredImportServiceImpl implements EventParticipantsUnRegisteredImportService {
	
	@Value(value = "${fms.import.file-path.volunteer-unregistered-list}")
	private String eventParticipantsUnRegisteredFilePath;
	

	@Value(value = "${fms.participation.type.unregistered}")
	private String participantStatus;
	
	@Autowired
	private EventParticipantDetailService eventParticipantDetailService;

	@Override
	public void importEventParticipantsUnregisteredFromFileLocal() throws FmsApplicationException, IOException {
		
		File eventParticipantUnRegisteredDetailsFile = new File(eventParticipantsUnRegisteredFilePath);
		
		//Validate if excel file
		if(!eventParticipantUnRegisteredDetailsFile.getName().endsWith(".xls") && !eventParticipantUnRegisteredDetailsFile.getName().endsWith(".xlsx")) {
			throw new FmsApplicationException("Invalid file type in filepath : " + eventParticipantUnRegisteredDetailsFile.getCanonicalPath() + ". Expected : Excel file.");
		}
		
		Workbook eventParticipantUnRegisteredDetailsWorkbook = ExcelUtils.getExcelWorkbook(eventParticipantUnRegisteredDetailsFile);
	
		Sheet eventParticipantUnRegisteredsDetailSheet = eventParticipantUnRegisteredDetailsWorkbook.getSheetAt(0);
		
		List<EventParticipantDetailDto> eventParticipantUnRegisteredDetailDtoList = this.parseEventParticipantUnRegisteredDetailsSheet(eventParticipantUnRegisteredsDetailSheet);
		
		for(EventParticipantDetailDto eventParticipantUnRegisteredDetailDto : eventParticipantUnRegisteredDetailDtoList) {
			
			eventParticipantDetailService.saveEventParticipantDetails(eventParticipantUnRegisteredDetailDto);
			
		}
		
	}

	private List<EventParticipantDetailDto> parseEventParticipantUnRegisteredDetailsSheet(Sheet eventParticipantUnRegisteredsDetailSheet) {
		
		List<EventParticipantDetailDto> eventParticipantUnRegisteredDetailDtoList = new ArrayList<EventParticipantDetailDto>();
		
		Iterator<Row> rowIterator = eventParticipantUnRegisteredsDetailSheet.iterator();
		
		//Get Header
		Row headerRow = rowIterator.next();
		
		//Get Header Column Names
		List<String> headerColumnNames = getHeaderColumnNames(headerRow);
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			
			if(!isRowEmpty(row)) {
				
				EventParticipantDetailDto eventParticipantUnRegisteredDetailDto = new EventParticipantDetailDto();
				
				//Event ID
				Cell eventIdCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPU_EVENT_ID));
				if(!isCellEmpty(eventIdCell)) {
					eventParticipantUnRegisteredDetailDto.setEventId(getCellValueAsString(eventIdCell));
				}
				
				//User ID or Employee ID
				Cell userIdCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPU_EMPLOYEE_ID));
				if(!isCellEmpty(userIdCell)) {
					eventParticipantUnRegisteredDetailDto.setUserId(Long.parseLong(getCellValueAsString(userIdCell)));
				}
				
				//Setting username same as userId
				eventParticipantUnRegisteredDetailDto.setUserName(String.valueOf(eventParticipantUnRegisteredDetailDto.getUserId()));
				
				eventParticipantUnRegisteredDetailDto.setParticipationStatus(participantStatus);
				
				eventParticipantUnRegisteredDetailDtoList.add(eventParticipantUnRegisteredDetailDto);
			}

		}
		
		return eventParticipantUnRegisteredDetailDtoList;
	
	}

	
}
