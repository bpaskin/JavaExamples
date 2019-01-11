package com.ibm.example.cdi.decorator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public class LoggingDecorator implements Logging {
	
	  @Inject @Delegate @Any
	  private Logging logging;
	  
	  private static int counter = 0;

	@Override
	public String writeLogEntry(String message) {
		return ++counter + " : " + logging.writeLogEntry(message);
	} 
}
