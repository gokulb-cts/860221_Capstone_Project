package com.cts.capstone.fms.exception;

import org.springframework.security.access.AccessDeniedException;

public class FmsAccessDeniedException extends AccessDeniedException {

	public FmsAccessDeniedException(String msg) {
		super(msg);
	}

}
