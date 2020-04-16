package com.cts.capstone.fms.service;

import java.io.IOException;

import com.cts.capstone.fms.exception.FmsApplicationException;

public interface EventsImportService {

	void importEventsFromFileLocal() throws IOException, FmsApplicationException;

}
