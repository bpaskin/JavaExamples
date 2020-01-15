package com.ibm.jaxws;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import com.ibm.jaxws.services.FullMoonInfo;

@WebServlet("/")
public class Configuration extends CXFNonSpringServlet {
	private static final long serialVersionUID = 4290096155177314228L;

	
	  @Override
	  public void loadBus(ServletConfig servletConfig) {
	    super.loadBus(servletConfig);
	    Bus bus = getBus();
	    BusFactory.setDefaultBus(bus);
	    Endpoint.publish("/Witcher", new FullMoonInfo());
	    System.out.println("service is defined!");
	  }
}
