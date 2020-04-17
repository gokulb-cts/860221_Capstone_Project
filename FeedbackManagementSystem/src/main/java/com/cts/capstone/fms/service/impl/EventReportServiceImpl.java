package com.cts.capstone.fms.service.impl;

import static com.cts.capstone.fms.constants.EventReportConstants.xlErSheetHeaders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.dto.EventReportDto;
import com.cts.capstone.fms.dto.MailDto;
import com.cts.capstone.fms.exception.UserNotFoundException;
import com.cts.capstone.fms.mail.MailSenderHelper;
import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.service.EventReportService;

@Service
public class EventReportServiceImpl implements EventReportService {
	
	@Autowired
	private FmsUserRepository userRepository;
	
	@Autowired
	private MailSenderHelper mailSenderHelper;
	
	@Override
	public ByteArrayInputStream generateEventReportWorkbook(List<EventReportDto> eventReportDtoList) throws IOException {

		try (
				// Define new workbook and outstream
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
			) {

			// Create new sheet
			Sheet eventDetailsSheet = workbook.createSheet("Event Details");

			/** HEADER **/
			// Define header font and styles
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE1.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle.setWrapText(true);
			headerCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			// Header Row
			Row headerRow = eventDetailsSheet.createRow(0);

			// Create Header
			for (int col = 0; col < xlErSheetHeaders.size(); col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(xlErSheetHeaders.get(col));
				cell.setCellStyle(headerCellStyle);
				
			}

			/*** Data Rows ***/
			// CellStyle for Date	
			/*
			 * CellStyle dateCellStyle = workbook.createCellStyle();
			 * dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
			 * "dd-MM-yy"));
			 */

			int rowIdx = 1;
			for (EventReportDto eventReportDto : eventReportDtoList) {
				Row row = eventDetailsSheet.createRow(rowIdx);
				row.createCell(0).setCellValue(eventReportDto.getEventId());
				row.createCell(1).setCellValue(eventReportDto.getEventName());
				row.createCell(2).setCellValue(eventReportDto.getMonth());
				row.createCell(3).setCellValue(eventReportDto.getEventDescription());
				row.createCell(4).setCellValue(eventReportDto.getEventDate());
				row.createCell(5).setCellValue(eventReportDto.getBaseLocation());
				row.createCell(6).setCellValue(eventReportDto.getBeneficiaryName());
				row.createCell(7).setCellValue(eventReportDto.getCouncilName());
				row.createCell(8).setCellValue(eventReportDto.getProject());
				row.createCell(9).setCellValue(eventReportDto.getCategory());
				row.createCell(10).setCellValue(eventReportDto.getTotalNoOfVolunteers());
				row.createCell(11).setCellValue(eventReportDto.getTotalVolunteerHours());
				row.createCell(12).setCellValue(eventReportDto.getTotalTravelHours());
				row.createCell(13).setCellValue(eventReportDto.getOverallVolunteeringHours());
				row.createCell(14).setCellValue(eventReportDto.getLivesImpacted());
				row.createCell(15).setCellValue(eventReportDto.getAverageRating());
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}

	}
	
	
	@Override
	public void sendEventReportViaMail(Long userId, List<EventReportDto> eventReportDtoList) throws MessagingException, IOException, UserNotFoundException {
		
		FmsUser user = userRepository.findByUserId(userId);
        if(user == null) throw new UserNotFoundException("User not found with User ID :" + userId);
        ByteArrayInputStream inputStream = this.generateEventReportWorkbook(eventReportDtoList);
                
        MailDto mailDto = new MailDto();
        String toMailAddress = "bgokul95@gmail.com"; //user.getEmailId();
        String content = "***This is a System Generated Mail. Please do not reply***";
        mailDto.setToMailAddress(toMailAddress);
        mailDto.setSubject("Event Report");
        mailDto.setBodyContent(content);
        mailDto.setAttachmentFileName("Event-Report.xlsx");
        mailDto.setAttachmentDataSource(new ByteArrayDataSource(inputStream,"application/vnd.ms-excel"));
        mailDto.setDataSourceType("application/vnd.ms-excel");
        
        if(!StringUtils.isEmpty(toMailAddress)) {
        	mailSenderHelper.sendMail(mailDto);
        }
        else {
        	throw new UserNotFoundException("Email Address not found for given User ID :" + userId);
        }
        		
	}
	

}
