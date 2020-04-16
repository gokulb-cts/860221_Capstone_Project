package com.cts.capstone.fms.service.impl;

import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_ACTIVITY_TYPE;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_BASE_LOCATION;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_BENEFICIARY_NAME;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_CATEGORY;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_COUNCIL_NAME;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_EVENT_DATE;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_EVENT_DESCRIPTION;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_EVENT_ID;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_EVENT_NAME;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_LIVES_IMPACTED;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_MONTH;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_OVERALL_VOLUNTEERING_HOURS;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_POC_CONTACT_NO;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_POC_ID;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_POC_NAME;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_PROJECT;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_STATUS;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_TOTAL_NO_OF_VOLUNTEERS;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_TOTAL_TRAVEL_HOURS;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_TOTAL_VOLUNTEER_HOURS;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.COL_EDS_VENUE_ADDRESS;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.XL_DATA_SEPARATOR;
import static com.cts.capstone.fms.constants.EventDetailImportConstants.XL_EDS_DATE_FORMAT;
import static com.cts.capstone.fms.util.ExcelUtils.getCellValueAsString;
import static com.cts.capstone.fms.util.ExcelUtils.getHeaderColumnNames;
import static com.cts.capstone.fms.util.ExcelUtils.isCellEmpty;
import static com.cts.capstone.fms.util.ExcelUtils.isRowEmpty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cts.capstone.fms.dto.EventDto;
import com.cts.capstone.fms.dto.FmsUserDto;
import com.cts.capstone.fms.exception.FmsApplicationException;
import com.cts.capstone.fms.service.EventService;
import com.cts.capstone.fms.service.EventsImportService;
import com.cts.capstone.fms.util.ExcelUtils;
import com.cts.capstone.fms.util.FmsApplicationUtils;

@Service
public class EventsImportServiceImpl implements EventsImportService {
	
	@Value(value = "${fms.import.file-path.event-info-list}")
	private String eventDetailLocalFilePath;
	
	@Autowired
	private EventService eventService;

	@Override
	public void importEventsFromFileLocal() throws IOException, FmsApplicationException {

		File eventDetailsFile = new File(eventDetailLocalFilePath);
		
		//Validate if excel file
		if(!eventDetailsFile.getName().endsWith(".xls") && !eventDetailsFile.getName().endsWith(".xlsx")) {
			throw new FmsApplicationException("Invalid file type in filepath : " + eventDetailsFile.getCanonicalPath() + ". Expected : Excel file.");
		}
		
		Workbook eventDetailsWorkbook = ExcelUtils.getExcelWorkbook(eventDetailsFile);
	
		//Events Summary Sheet
		Sheet detailSheet = eventDetailsWorkbook.getSheetAt(0);
		
		List<EventDto> eventDtoList = this.parseWorkbookEventDetailsSheet(detailSheet);
		
		for(EventDto eventDto : eventDtoList) {
			
			eventService.saveEvent(eventDto);
			
		}
		
	}

	
	
	
	private List<EventDto> parseWorkbookEventDetailsSheet(Sheet eventDetailsSheet) throws FmsApplicationException {
		
		List<EventDto> eventDtoList = new ArrayList<EventDto>();
		
		Iterator<Row> rowIterator = eventDetailsSheet.iterator();
		
		//Get Header
		Row headerRow = rowIterator.next();
		
		//Get Header Column Names
		List<String> headerColumnNames = getHeaderColumnNames(headerRow);
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			
			if(!isRowEmpty(row)) {
				
				EventDto eventDto = new EventDto();
				
				Set<FmsUserDto> pocUsers;
				
				//Event ID
				Cell eventIdCell = row.getCell(headerColumnNames.indexOf(COL_EDS_EVENT_ID));
				if(!isCellEmpty(eventIdCell)) {
					eventDto.setEventId(getCellValueAsString(eventIdCell));
				}
				
				//Month
				Cell monthCell = row.getCell(headerColumnNames.indexOf(COL_EDS_MONTH));
				if(!isCellEmpty(monthCell)) {
					eventDto.setMonth(getCellValueAsString(monthCell));
				}
				
				//Base Location
				Cell baseLocCell = row.getCell(headerColumnNames.indexOf(COL_EDS_BASE_LOCATION));
				if(!isCellEmpty(baseLocCell)) {
					eventDto.setBaseLocation(getCellValueAsString(baseLocCell));
				}
				
				//Beneficiary Name
				Cell beneficiaryNameCell = row.getCell(headerColumnNames.indexOf(COL_EDS_BENEFICIARY_NAME));
				if(!isCellEmpty(beneficiaryNameCell)) {
					eventDto.setBeneficiaryName(getCellValueAsString(beneficiaryNameCell));
				}
				
				//Venue Address
				Cell venueCell = row.getCell(headerColumnNames.indexOf(COL_EDS_VENUE_ADDRESS));
				if(!isCellEmpty(venueCell)) {
					eventDto.setVenueAddress(getCellValueAsString(venueCell));
				}
				
				//Council Name
				Cell councilNameCell = row.getCell(headerColumnNames.indexOf(COL_EDS_COUNCIL_NAME));
				if(!isCellEmpty(councilNameCell)) {
					eventDto.setCouncilName(getCellValueAsString(councilNameCell));
				}
				
				//Project
				Cell projectCell = row.getCell(headerColumnNames.indexOf(COL_EDS_PROJECT));
				if(!isCellEmpty(projectCell)) {
					eventDto.setProject(getCellValueAsString(projectCell));
				}
				
				//Category
				Cell categoryCell = row.getCell(headerColumnNames.indexOf(COL_EDS_CATEGORY));
				if(!isCellEmpty(categoryCell)) {
					eventDto.setCategory(getCellValueAsString(categoryCell));
				}
				
				//Event Name
				Cell eventNameCell = row.getCell(headerColumnNames.indexOf(COL_EDS_EVENT_NAME));
				if(!isCellEmpty(eventNameCell)) {
					eventDto.setEventName(getCellValueAsString(eventNameCell));
				}
				
				//Event Description
				Cell eventDescCell = row.getCell(headerColumnNames.indexOf(COL_EDS_EVENT_DESCRIPTION));
				if(!isCellEmpty(eventDescCell)) {
					eventDto.setEventDescription(getCellValueAsString(eventDescCell));
				}
				
				//Event Date
				Cell eventDateCell = row.getCell(headerColumnNames.indexOf(COL_EDS_EVENT_DATE));
				if(!isCellEmpty(eventIdCell)) {
					String dateString = getCellValueAsString(eventDateCell);
					//Parse Date from String
					Date eventDate = FmsApplicationUtils.getFormattedDateFromString(dateString, XL_EDS_DATE_FORMAT);
					eventDto.setEventDate(eventDate);
				}
				
				//Total Number of Volunteers
				Cell totalVolunteersCell = row.getCell(headerColumnNames.indexOf(COL_EDS_TOTAL_NO_OF_VOLUNTEERS));
				if(!isCellEmpty(totalVolunteersCell)) {
					eventDto.setTotalNoOfVolunteers(Integer.valueOf(getCellValueAsString(totalVolunteersCell)));
				}
				
				//Total Volunteer Hours
				Cell totalVolunteerHrsCell = row.getCell(headerColumnNames.indexOf(COL_EDS_TOTAL_VOLUNTEER_HOURS));
				if(!isCellEmpty(totalVolunteerHrsCell)) {
					eventDto.setTotalVolunteerHours(Integer.valueOf(getCellValueAsString(totalVolunteerHrsCell)));
				}
				
				//Total Travel Hours
				Cell totalTravelHrsCell = row.getCell(headerColumnNames.indexOf(COL_EDS_TOTAL_TRAVEL_HOURS));
				if(!isCellEmpty(totalTravelHrsCell)) {
					eventDto.setTotalTravelHours(Integer.valueOf(getCellValueAsString(totalTravelHrsCell)));
				}
				
				//Overall Volunteering Hours
				Cell overallVolunteerHrsCell = row.getCell(headerColumnNames.indexOf(COL_EDS_OVERALL_VOLUNTEERING_HOURS));
				if(!isCellEmpty(overallVolunteerHrsCell)) {
					eventDto.setOverallVolunteeringHours(Integer.valueOf(getCellValueAsString(overallVolunteerHrsCell)));
				}
				
				//Lives Impacted
				Cell livesImpactedCell = row.getCell(headerColumnNames.indexOf(COL_EDS_LIVES_IMPACTED));
				if(!isCellEmpty(livesImpactedCell)) {
					eventDto.setLivesImpacted(Integer.valueOf(getCellValueAsString(livesImpactedCell)));
				}
				
				//Activity Type
				Cell activityTypeCell = row.getCell(headerColumnNames.indexOf(COL_EDS_ACTIVITY_TYPE));
				if(!isCellEmpty(activityTypeCell)) {
					eventDto.setActivityType(Integer.valueOf(getCellValueAsString(activityTypeCell)));
				}
				
				//Status
				Cell statusCell = row.getCell(headerColumnNames.indexOf(COL_EDS_STATUS));
				if(!isCellEmpty(statusCell)) {
					eventDto.setStatus(getCellValueAsString(statusCell));
				}
				
				//POC ID
				Cell pocIdCell = row.getCell(headerColumnNames.indexOf(COL_EDS_POC_ID));
				String pocUserIds = null;
				if(!isCellEmpty(pocIdCell)) {
					pocUserIds = getCellValueAsString(pocIdCell);
				}
				
				//POC Name
				Cell pocNameCell = row.getCell(headerColumnNames.indexOf(COL_EDS_POC_NAME));
				String pocNames = null;
				if(!isCellEmpty(pocNameCell)) {
					pocNames = getCellValueAsString(pocNameCell);
				}
				
				//POC Contact Number
				Cell pocContactNoCell = row.getCell(headerColumnNames.indexOf(COL_EDS_POC_CONTACT_NO));
				String pocContactNos = null;
				if(!isCellEmpty(pocContactNoCell)) {
					pocContactNos = getCellValueAsString(pocContactNoCell);
				}
				
				pocUsers = this.getPocUsersForSheetData(pocUserIds, pocNames, pocContactNos);
				
				eventDto.setPocUsers(pocUsers);
				
				eventDtoList.add(eventDto);
			}

		}
		
		return eventDtoList;
	}

	private Set<FmsUserDto> getPocUsersForSheetData(String pocUserIds, String pocNames, String pocContactNos) throws FmsApplicationException {
		Set<FmsUserDto> pocUsers = new HashSet<FmsUserDto>();
		
		if(!StringUtils.isEmpty(pocUserIds)) {
			
			List<String> pocUserIdList = Arrays.asList(pocUserIds.split(XL_DATA_SEPARATOR));
			List<String> pocNameList = new ArrayList<String>();
			List<String> pocContactList = new ArrayList<String>();
			
			int pocUserIdsLen = pocUserIdList.size();
			int pocNamesLen = 0;
			int pocContactsLen = 0;
			
			
			if(!StringUtils.isEmpty(pocNames)) {
				pocNameList = Arrays.asList(pocNames.split(XL_DATA_SEPARATOR));
				pocNamesLen = pocNameList.size();
			}
			
			if(!StringUtils.isEmpty(pocContactNos)) {
				pocContactList = Arrays.asList(pocContactNos.split(XL_DATA_SEPARATOR));
				pocContactsLen = pocContactList.size();
			}
			
			if(pocUserIdsLen != pocNamesLen || pocUserIdsLen != pocContactsLen) {
					throw new FmsApplicationException("Cannot Parse as POC details are inconsistent");
			}
			else {
				
				for(int idx = 0; idx < pocUserIdsLen; idx++) {
					
					FmsUserDto userDto = new FmsUserDto();
					userDto.setUserId(Long.parseLong(pocUserIdList.get(idx)));
					
					if(pocNamesLen > 0)
						userDto.setUserName(pocNameList.get(idx));
					
					if(pocContactsLen > 0) 
						userDto.setMobileNumber(pocContactList.get(idx));
					
					pocUsers.add(userDto);
					
				}
				
			}
			
		}
		
		return pocUsers;
	}

}
