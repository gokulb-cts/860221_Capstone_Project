package com.cts.capstone.fms.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FmsExceptionResponse {
	private String message;
	private String details;
	private Date timestamp;
}
