package com.cts.capstone.fms.constants;

import java.util.Arrays;
import java.util.List;

public class EventReportConstants {

	public static final String EVENTS_REPORT_END_POINT = "/events/report";
	
	
	/*** Events Report Excel ***/
	
	//Headers Column
	public static final String COL_ER_EVENT_ID = "Event ID";
	public static final String COL_ER_EVENT_NAME = "Event Name";
	public static final String COL_ER_MONTH = "Month";
	public static final String COL_ER_EVENT_DESCRIPTION = "Event Description";
	public static final String COL_ER_EVENT_DATE = "Event Date (DD-MM-YY)";
	public static final String COL_ER_BASE_LOCATION = "Base Location";
	public static final String COL_ER_BENEFICIARY_NAME = "Beneficiary Name";
	public static final String COL_ER_COUNCIL_NAME = "Council Name";
	public static final String COL_ER_PROJECT = "Project";
	public static final String COL_ER_CATEGORY = "Category";
	public static final String COL_ER_TOTAL_NO_OF_VOLUNTEERS = "Total no. of volunteers";
	public static final String COL_ER_TOTAL_VOLUNTEER_HOURS = "Total Volunteer Hours";
	public static final String COL_ER_TOTAL_TRAVEL_HOURS = "Total Travel Hours";
	public static final String COL_ER_OVERALL_VOLUNTEERING_HOURS = "Overall Volunteering Hours";
	public static final String COL_ER_LIVES_IMPACTED = "Lives Impacted";
	public static final String COL_ER_AVERAGE_RATING = "POC Contact Number";
	
	private static final String[] xlErSheetHeaderArr = { 	COL_ER_EVENT_ID,
															COL_ER_MONTH,
															COL_ER_BASE_LOCATION,
															COL_ER_BENEFICIARY_NAME,
															COL_ER_COUNCIL_NAME,
															COL_ER_PROJECT,
															COL_ER_CATEGORY,
															COL_ER_EVENT_NAME,
															COL_ER_EVENT_DESCRIPTION,
															COL_ER_EVENT_DATE,
															COL_ER_TOTAL_NO_OF_VOLUNTEERS,
															COL_ER_TOTAL_VOLUNTEER_HOURS,
															COL_ER_TOTAL_TRAVEL_HOURS,
															COL_ER_OVERALL_VOLUNTEERING_HOURS,
															COL_ER_LIVES_IMPACTED,
															COL_ER_AVERAGE_RATING 	};
	
	//Excel Font Styles and settings
	public static final List<String> xlErSheetHeaders = Arrays.asList(xlErSheetHeaderArr); 
	
	public static final Integer maxNumOfColumns = 35;

	
}
