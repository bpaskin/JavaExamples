package com.ibm.example.cdi.decorator;

public class LoggingEntry implements Logging {

	@Override
	public String writeLogEntry(String message) {
		return message;
	}

}
