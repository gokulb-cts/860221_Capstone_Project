package com.cts.capstone.fms.constants;

public class EventDetailImportConstants {

	public static final String EVENT_DETAIL_IMPORT_END_POINT = "/event-detail/import";

	
	//Workbook Data
	
	
	/** OutReach-Event Summary - Event Details Workbook Constants - Starts **/
	
	//Events Summary sheet - EDS : (E)vent (D)etail (S)ummary

	//Header column names
	public static final String COL_EDS_EVENT_ID = "Event ID";
	public static final String COL_EDS_MONTH = "Month";
	public static final String COL_EDS_BASE_LOCATION = "Base Location";
	public static final String COL_EDS_BENEFICIARY_NAME = "Beneficiary Name";
	public static final String COL_EDS_VENUE_ADDRESS = "Venue Address";	
	public static final String COL_EDS_COUNCIL_NAME = "Council Name";
	public static final String COL_EDS_PROJECT = "Project";
	public static final String COL_EDS_CATEGORY = "Category";
	public static final String COL_EDS_EVENT_NAME = "Event Name";
	public static final String COL_EDS_EVENT_DESCRIPTION = "Event Description";
	public static final String COL_EDS_EVENT_DATE = "Event Date (DD-MM-YY)";
	public static final String COL_EDS_TOTAL_NO_OF_VOLUNTEERS = "Total no. of volunteers";
	public static final String COL_EDS_TOTAL_VOLUNTEER_HOURS = "Total Volunteer Hours";
	public static final String COL_EDS_TOTAL_TRAVEL_HOURS = "Total Travel Hours";
	public static final String COL_EDS_OVERALL_VOLUNTEERING_HOURS = "Overall Volunteering Hours";
	public static final String COL_EDS_LIVES_IMPACTED = "Lives Impacted";
	public static final String COL_EDS_ACTIVITY_TYPE = "Activity Type";
	public static final String COL_EDS_STATUS = "Status";
	public static final String COL_EDS_POC_ID = "POC ID";
	public static final String COL_EDS_POC_NAME = "POC Name";
	public static final String COL_EDS_POC_CONTACT_NO = "POC Contact Number";
	
	/*private static final String[] xlEdsSheetHeaderArr = { COL_EDS_EVENT_ID,
														COL_EDS_MONTH,
														COL_EDS_BASE_LOCATION,
														COL_EDS_BENEFICIARY_NAME,
														COL_EDS_VENUE_ADDRESS,
														COL_EDS_COUNCIL_NAME,
														COL_EDS_PROJECT,
														COL_EDS_CATEGORY,
														COL_EDS_EVENT_NAME,
														COL_EDS_EVENT_DESCRIPTION,
														COL_EDS_EVENT_DATE,
														COL_EDS_TOTAL_NO_OF_VOLUNTEERS,
														COL_EDS_TOTAL_VOLUNTEER_HOURS,
														COL_EDS_TOTAL_TRAVEL_HOURS,
														COL_EDS_OVERALL_VOLUNTEERING_HOURS,
														COL_EDS_LIVES_IMPACTED,
														COL_EDS_ACTIVITY_TYPE,
														COL_EDS_STATUS,
														COL_EDS_POC_ID,
														COL_EDS_POC_NAME,
														COL_EDS_POC_CONTACT_NO }; 
	
	public static final List<String> xlEdsSheetHeaders = Arrays.asList(xlEdsSheetHeaderArr);*/
	
	public static final String XL_EDS_DATE_FORMAT = "dd-MM-yy";
	
	public static final String XL_DATA_SEPARATOR = ";"; //Poc usernames, Ids separator
	/** OutReach-Event Summary - Event Details Workbook Constants - Ends **/
	
	/*** OutReach-Event Information - Event Participants Details - Starts ***/
	
	//Existing Event Details Sheet -  EPS - Event Participants Summary 
	
	//Header column names
	public static final String COL_EPS_EVENT_ID = "Event ID";
	public static final String COL_EPS_BASE_LOCATION = "Base Location";
	public static final String COL_EPS_BENEFICIARY_NAME = "Beneficiary Name";
	public static final String COL_EPS_COUNCIL_NAME = "Council Name";
	public static final String COL_EPS_EVENT_NAME = "Event Name";
	public static final String COL_EPS_EVENT_DESCRIPTION = "Event Description";
	public static final String COL_EPS_EVENT_DATE = "Event Date (DD-MM-YY)";
	public static final String COL_EPS_EMPLOYEE_ID = "Employee ID";
	public static final String COL_EPS_EMPLOYEE_NAME = "Employee Name";
	public static final String COL_EPS_VOLUNTEER_HOURS = "Volunteer Hours";
	public static final String COL_EPS_TRAVEL_HOURS = "Travel Hours";
	public static final String COL_EPS_LIVES_IMPACTED = "Lives Impacted";
	public static final String COL_EPS_BUSINESS_UNIT = "Business Unit";
	public static final String COL_EPS_STATUS = "Status";
	public static final String COL_EPS_IIEP_CATEGORY = "IIEP Category";
	
	/*** OutReach-Event Information - Event Participants Details - Ends ***/
	
	/*** Voluntary Enrollment Details - Not Attended - Starts ***/
	
	//Existing Event Details Sheet -  ENPS - Event Non-Participants Summary 
	
	//Header column names
	public static final String COL_ENPS_EVENT_ID = "Event ID";
	public static final String COL_ENPS_BASE_LOCATION = "Base Location";
	public static final String COL_ENPS_BENEFICIARY_NAME = "Beneficiary Name";
	public static final String COL_ENPS_EVENT_NAME = "Event Name";
	public static final String COL_ENPS_EVENT_DATE = "Event Date (DD-MM-YY)";
	public static final String COL_ENPS_EMPLOYEE_ID = "EmployeeID";
	
	/*** Voluntary Enrollment Details - Not Attended - Ends ***/
	
	/*** Voluntary Enrollment Details - UnRegistered - Starts ***/
	
	//Existing Event Details Sheet -  ENPS - Event Participants UnRegistered Summary 
	
	//Header column names
	public static final String COL_EPU_EVENT_ID = "Event ID";
	public static final String COL_EPU_BASE_LOCATION = "Base Location";
	public static final String COL_EPU_BENEFICIARY_NAME = "Beneficiary Name";
	public static final String COL_EPU_EVENT_NAME = "Event Name";
	public static final String COL_EPU_EVENT_DATE = "Event Date (DD-MM-YY)";
	public static final String COL_EPU_EMPLOYEE_ID = "EmployeeID";
	
	/*** Voluntary Enrollment Details - UnRegistered - Ends ***/

}
