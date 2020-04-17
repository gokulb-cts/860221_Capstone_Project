package com.cts.capstone.fms.dto;

import javax.mail.util.ByteArrayDataSource;

import lombok.Data;

@Data
public class MailDto {
	
	private String toMailAddress;
	
	private String subject;
	
	private String bodyContent;
	
	private Boolean htmlBodyContent = false;
	
	private String attachmentFileName;
	
	private ByteArrayDataSource attachmentDataSource;
	
	private String dataSourceType;
	
}
