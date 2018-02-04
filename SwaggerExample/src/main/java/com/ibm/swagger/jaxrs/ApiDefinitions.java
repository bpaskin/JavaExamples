package com.ibm.swagger.jaxrs;

import java.util.logging.Logger;

import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import io.swagger.jaxrs.config.ReaderListener;
import io.swagger.models.Swagger;

@SwaggerDefinition
public class ApiDefinitions implements ReaderListener {
	
	private static String CLASSNAME = ApiDefinitions.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);


	@Override
	public void afterScan(Reader reader, Swagger swagger) {
		LOGGER.entering(CLASSNAME, "afterScan");
		LOGGER.exiting(CLASSNAME, "afterScan");
	}

	@Override
	public void beforeScan(Reader reader, Swagger swagger) {
		LOGGER.entering(CLASSNAME, "beforeScan", new Object[] {reader, swagger});
        ((DefaultReaderConfig) reader.getConfig()).setScanAllResources(true); 
		LOGGER.exiting(CLASSNAME, "beforeScan");
	}
} 
