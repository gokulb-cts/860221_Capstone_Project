package com.cts.capstone.fms.service;

import java.io.IOException;

import com.cts.capstone.fms.exception.FmsApplicationException;

public interface EventNonParticipantsImportService {

	public void importEventNonParticipantsFromFileLocal() throws FmsApplicationException, IOException;
	
}
