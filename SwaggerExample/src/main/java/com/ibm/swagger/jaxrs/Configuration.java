package com.ibm.swagger.jaxrs;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("services")
public class Configuration extends Application {
	
	private static String CLASSNAME = Configuration.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
    
	public Configuration() {
		LOGGER.entering(CLASSNAME, "Configuration()");
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("SwaggerExample");
        beanConfig.setBasePath("/SwaggerExample/services");
        beanConfig.setResourcePackage("com.ibm.swagger.jaxrs");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
		LOGGER.exiting(CLASSNAME, "Configuration()");
 	} 
}
