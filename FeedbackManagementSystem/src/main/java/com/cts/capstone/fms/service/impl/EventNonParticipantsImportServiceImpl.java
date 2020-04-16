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
import com.cts.capstone.fms.service.EventNonParticipantsImportService;
import com.cts.capstone.fms.service.EventParticipantDetailService;
import com.cts.capstone.fms.util.ExcelUtils;

@Service
public class EventNonParticipantsImportServiceImpl implements EventNonParticipantsImportService {

	@Value(value = "${fms.import.file-path.volunteer-unattended-list}")
	private String eventNonParticipantsFilePath;
	

	@Value(value = "${fms.participation.type.unattended}")
	private String participantStatus;
	
	@Autowired
	private EventParticipantDetailService eventParticipantDetailService;

	@Override
	public void importEventNonParticipantsFromFileLocal() throws FmsApplicationException, IOException {
		
		File eventNonParticipantDetailsFile = new File(eventNonParticipantsFilePath);
		
		//Validate if excel file
		if(!eventNonParticipantDetailsFile.getName().endsWith(".xls") && !eventNonParticipantDetailsFile.getName().endsWith(".xlsx")) {
			throw new FmsApplicationException("Invalid file type in filepath : " + eventNonParticipantDetailsFile.getCanonicalPath() + ". Expected : Excel file.");
		}
		
		Workbook eventNonParticipantDetailsWorkbook = ExcelUtils.getExcelWorkbook(eventNonParticipantDetailsFile);
	
		//Existing Event Details Sheet
		Sheet eventNonParticipantsDetailSheet = eventNonParticipantDetailsWorkbook.getSheetAt(0);
		
		List<EventParticipantDetailDto> eventNonParticipantDetailDtoList = this.parseEventNonParticipantDetailsSheet(eventNonParticipantsDetailSheet);
		
		for(EventParticipantDetailDto eventNonParticipantDetailDto : eventNonParticipantDetailDtoList) {
			
			eventParticipantDetailService.saveEventParticipantDetails(eventNonParticipantDetailDto);
			
		}
		
	}

	private List<EventParticipantDetailDto> parseEventNonParticipantDetailsSheet(Sheet eventNonParticipantsDetailSheet) {
		
		List<EventParticipantDetailDto> eventNonParticipantDetailDtoList = new ArrayList<EventParticipantDetailDto>();
		
		Iterator<Row> rowIterator = eventNonParticipantsDetailSheet.iterator();
		
		//Get Header
		Row headerRow = rowIterator.next();
		
		//Get Header Column Names
		List<String> headerColumnNames = getHeaderColumnNames(headerRow);
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			
			if(!isRowEmpty(row)) {
				
				EventParticipantDetailDto eventNonParticipantDetailDto = new EventParticipantDetailDto();
				
				//Event ID
				Cell eventIdCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_ENPS_EVENT_ID));
				if(!isCellEmpty(eventIdCell)) {
					eventNonParticipantDetailDto.setEventId(getCellValueAsString(eventIdCell));
				}
				
				//User ID or Employee ID
				Cell userIdCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_ENPS_EMPLOYEE_ID));
				if(!isCellEmpty(userIdCell)) {
					eventNonParticipantDetailDto.setUserId(Long.parseLong(getCellValueAsString(userIdCell)));
				}
				
				//Setting username same as userId
				eventNonParticipantDetailDto.setUserName(String.valueOf(eventNonParticipantDetailDto.getUserId()));
				
				eventNonParticipantDetailDto.setParticipationStatus(participantStatus);
				
				eventNonParticipantDetailDtoList.add(eventNonParticipantDetailDto);
			}

		}
		
		return eventNonParticipantDetailDtoList;
	
	}

	
}
