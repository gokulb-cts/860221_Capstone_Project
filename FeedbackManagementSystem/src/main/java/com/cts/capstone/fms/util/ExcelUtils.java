package com.cts.capstone.fms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	//private static final String legacyExcelFileExt = ".xls";
	
	//private static final String excelFileExt = ".xlsx";
	
	public static Workbook getExcelWorkbook(File file) throws IOException {
		FileInputStream fileStream = new FileInputStream(file);
		try {
			
			/*
			 * if(file.getName().endsWith(legacyExcelFileExt)) { return new
			 * HSSFWorkbook(fileStream); } else if(file.getName().endsWith(excelFileExt)) {
			 * return new XSSFWorkbook(fileStream); }
			 */
			return WorkbookFactory.create(fileStream);
		}
		catch(IOException e) {
			throw e;
		}
		finally {
			fileStream.close();
		}
		
		
	}
	

	public static boolean isCellEmpty(Cell cell) {
		return cell != null && cell.getCellType() == CellType.BLANK;
	}
	
	public static boolean isRowEmpty(Row row) {
	    DataFormatter dataFormatter = new DataFormatter();
	    if(row != null) {
	        for(Cell cell: row) {
	            if(dataFormatter.formatCellValue(cell).trim().length() > 0) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	public static String getCellValueAsString(Cell cell) {
		return new DataFormatter().formatCellValue(cell).trim();
	}
	
	public static List<String> getHeaderColumnNames(Row headerRow) {
		List<String> headerColNames = new ArrayList<String>();
		for(Cell cell : headerRow) {
			if(!isCellEmpty(cell))
				headerColNames.add(cell.getStringCellValue().replaceAll("[\\t\\r\\n]+", " "));
		}
		return headerColNames;
	}

}
