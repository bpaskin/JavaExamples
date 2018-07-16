package com.ibm.enterprise.session;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import com.ibm.websphere.management.AdminService;
import com.ibm.websphere.management.AdminServiceFactory;

@RunAs("LPAdmin")
@Stateless
public class ListenerPortService {
	private static String CLASSNAME = ListenerPortService.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
	private static AdminService adminService = AdminServiceFactory.getAdminService();
	private static String serverName = adminService.getProcessName();
	private static String nodeName = adminService.getNodeName();
	
	public void getStatus() {
		LOGGER.entering(CLASSNAME, "getStatus()");
		
		String signature[] = { };
		String params[] = { };
		
		try {
			ObjectName queryName = new ObjectName("WebSphere:node=" + nodeName + ",process=" + serverName + ",type=ListenerPort,*");
			Set<ObjectName> listenerPorts = adminService.queryNames(queryName, null);
			
			Iterator<ObjectName> it = listenerPorts.iterator();
			while (it.hasNext()) {
				ObjectName listenerPort = it.next();
				boolean isStarted = (boolean) adminService.invoke(listenerPort, "isStarted", params, signature);
				String lpName = listenerPort.getKeyProperty("name");
				
				if ( isStarted ) {
					LOGGER.fine(lpName  + " listener Port is started");
				} else {
					LOGGER.fine("starting " + lpName + " listener port");
					adminService.invoke(listenerPort, "start", params, signature);
				}
			}
			
			LOGGER.exiting(CLASSNAME, "getStatus()");
		} catch (MalformedObjectNameException | InstanceNotFoundException | MBeanException | ReflectionException  e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			LOGGER.exiting(CLASSNAME, "getStatus()");
		}
	}
}
