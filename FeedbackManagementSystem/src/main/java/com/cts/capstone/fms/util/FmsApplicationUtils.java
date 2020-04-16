package com.cts.capstone.fms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cts.capstone.fms.exception.FmsApplicationException;

public class FmsApplicationUtils {

	public static Date getFormattedDateFromString(String dateStr, String dateFormatPattern) throws FmsApplicationException {
		
		DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
		try {
			Date date = dateFormat.parse(dateStr);
			return date;
		}
		catch (Exception e) {
			throw new FmsApplicationException("Invalid Date Format - " + dateStr);
		}
		
	}
	
}
