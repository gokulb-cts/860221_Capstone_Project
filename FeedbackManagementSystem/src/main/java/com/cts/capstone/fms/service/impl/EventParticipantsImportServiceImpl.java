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
import com.cts.capstone.fms.service.EventParticipantsImportService;
import com.cts.capstone.fms.util.ExcelUtils;

@Service
public class EventParticipantsImportServiceImpl implements EventParticipantsImportService {

	@Value(value = "${fms.import.file-path.volunteer-attended-list}")
	private String eventParticipantsFilePath;
	

	@Value(value = "${fms.participation.type.attended}")
	private String participantStatus;
	
	@Autowired
	private EventParticipantDetailService eventParticipantDetailService;

	@Override
	public void importEventParticipantsFromFileLocal() throws FmsApplicationException, IOException {
		
		File eventParticipantDetailsFile = new File(eventParticipantsFilePath);
		
		//Validate if excel file
		if(!eventParticipantDetailsFile.getName().endsWith(".xls") && !eventParticipantDetailsFile.getName().endsWith(".xlsx")) {
			throw new FmsApplicationException("Invalid file type in filepath : " + eventParticipantDetailsFile.getCanonicalPath() + ". Expected : Excel file.");
		}
		
		Workbook eventParticipantDetailsWorkbook = ExcelUtils.getExcelWorkbook(eventParticipantDetailsFile);
	
		//Existing Event Details Sheet
		Sheet eventParticipantsDetailSheet = eventParticipantDetailsWorkbook.getSheetAt(0);
		
		List<EventParticipantDetailDto> eventParticipantDetailDtoList = this.parseEventParticipantDetailsSheet(eventParticipantsDetailSheet);
		
		for(EventParticipantDetailDto eventParticipantDetailDto : eventParticipantDetailDtoList) {
			
			eventParticipantDetailService.saveEventParticipantDetails(eventParticipantDetailDto);
			
		}
		
	}

	private List<EventParticipantDetailDto> parseEventParticipantDetailsSheet(Sheet eventParticipantsDetailSheet) {
		
		List<EventParticipantDetailDto> eventParticipantDetailDtoList = new ArrayList<EventParticipantDetailDto>();
		
		Iterator<Row> rowIterator = eventParticipantsDetailSheet.iterator();
		
		//Get Header
		Row headerRow = rowIterator.next();
		
		//Get Header Column Names
		List<String> headerColumnNames = getHeaderColumnNames(headerRow);
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			
			if(!isRowEmpty(row)) {
				
				EventParticipantDetailDto eventParticipantDetailDto = new EventParticipantDetailDto();
				
				//Event ID
				Cell eventIdCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPS_EVENT_ID));
				if(!isCellEmpty(eventIdCell)) {
					eventParticipantDetailDto.setEventId(getCellValueAsString(eventIdCell));
				}
				
				//User ID or Employee ID
				Cell userIdCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPS_EMPLOYEE_ID));
				if(!isCellEmpty(userIdCell)) {
					eventParticipantDetailDto.setUserId(Long.parseLong(getCellValueAsString(userIdCell)));
				}
				
				//User Name or Employee Name
				Cell userNameCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPS_EMPLOYEE_NAME));
				if(!isCellEmpty(userNameCell)) {
					eventParticipantDetailDto.setUserName(getCellValueAsString(userNameCell));
				}
				
				//Volunteer Hours
				Cell volunteerHrsCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPS_VOLUNTEER_HOURS));
				if(!isCellEmpty(volunteerHrsCell)) {
					eventParticipantDetailDto.setVolunteerHours(Integer.parseInt(getCellValueAsString(volunteerHrsCell)));
				}
				
				//Travel Hours
				Cell travelHrsCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPS_TRAVEL_HOURS));
				if(!isCellEmpty(travelHrsCell)) {
					eventParticipantDetailDto.setTravelHours(Integer.parseInt(getCellValueAsString(travelHrsCell)));
				}
				
				//Business Unit
				Cell businessUnitCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPS_BUSINESS_UNIT));
				if(!isCellEmpty(businessUnitCell)) {
					eventParticipantDetailDto.setBusinessUnit(getCellValueAsString(businessUnitCell));
				}
				
				//IIEP Category
				Cell iiepCategoryCell = row.getCell(headerColumnNames.indexOf(EventDetailImportConstants.COL_EPS_IIEP_CATEGORY));
				if(!isCellEmpty(iiepCategoryCell)) {
					eventParticipantDetailDto.setIiepCategory(getCellValueAsString(iiepCategoryCell));
				}
				
				eventParticipantDetailDto.setParticipationStatus(participantStatus);
				
				eventParticipantDetailDtoList.add(eventParticipantDetailDto);
			}

		}
		
		return eventParticipantDetailDtoList;
	
	}

}
