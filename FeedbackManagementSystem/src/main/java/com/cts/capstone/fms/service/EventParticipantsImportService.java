package com.cts.capstone.fms.service;

import java.io.IOException;

import com.cts.capstone.fms.exception.FmsApplicationException;

public interface EventParticipantsImportService {

	void importEventParticipantsFromFileLocal()  throws FmsApplicationException, IOException ;

}
