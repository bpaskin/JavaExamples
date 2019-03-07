package com.ibm.example.spring.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {
	
	private static final String CONFIG_LOCATION = "com.ibm.example.spring.config";
    private static final String MAPPING_URL = "/*";
	
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>> ONSTARTUP <<<<<<<<<<<<<<<<<<<<<<<<");
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(MAPPING_URL);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> ONSTARTUP END <<<<<<<<<<<<<<<<<<<<<<<<");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }
}
