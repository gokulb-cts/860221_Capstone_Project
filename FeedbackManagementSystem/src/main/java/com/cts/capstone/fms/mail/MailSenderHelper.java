package com.cts.capstone.fms.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.cts.capstone.fms.dto.MailDto;

@Component
public class MailSenderHelper {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(MailDto mailDto) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(mailDto.getToMailAddress());
        helper.setSubject(mailDto.getSubject());
        helper.setText(mailDto.getBodyContent(), mailDto.getHtmlBodyContent());
        if(mailDto.getAttachmentDataSource() != null) {
        	helper.addAttachment(mailDto.getAttachmentFileName(), mailDto.getAttachmentDataSource());
        }
        javaMailSender.send(message);
	}
	
}
