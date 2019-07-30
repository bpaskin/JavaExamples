package com.ibm.pmi.singleton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import com.ibm.pmi.bean.ResourceBean;
import com.ibm.pmi.bean.Serverbean;
import com.ibm.websphere.cache.DistributedObjectCache;
import com.ibm.websphere.management.AdminService;
import com.ibm.websphere.management.AdminServiceFactory;
import com.ibm.websphere.pmi.PmiModuleConfig;
import com.ibm.websphere.pmi.client.PmiClient;
import com.ibm.websphere.pmi.stat.WSCountStatistic;
import com.ibm.websphere.pmi.stat.WSRangeStatistic;
import com.ibm.websphere.pmi.stat.WSStatistic;
import com.ibm.websphere.pmi.stat.WSStats;
import com.ibm.websphere.pmi.stat.WSTimeStatistic;


// Run as a Singleton at Statup with the Admin Privileges if App security is turned on.
@Singleton
@Startup
@DeclareRoles({"PMIAdministrator"})
@RunAs("PMIAdministrator")
@SuppressWarnings("deprecation")
public class PMIStats {

	// name to store the statistics in for the Distributed Object Cache
	// cache/PMIStats needs to be setup within WAS
	private static String DOC_NAME = "cache/PMIStats";
	
	// mbeans to lookup.  This can be changed to meet the need
	private static String[] mbeansList = {"ThreadPool", "ConnectionPool"};
			
	// Schedule to collect the PMI states every 15 seconds
	@RolesAllowed({"PMIAdministrator"})
	@Schedule(second="*/15", hour="*", minute="*", persistent=false)
	public void gatherPMIStats() {

		try {
			
			// get the settings from the Distributed Object Cache
			InitialContext context = new InitialContext();
			DistributedObjectCache doc = (DistributedObjectCache) context.lookup(DOC_NAME);
			
			// Connect to the server and get specific information
			AdminService adminService = AdminServiceFactory.getAdminService();
			
			// lookup the node name and server name of the current server
			Serverbean sb = new Serverbean();
			sb.setServerName(adminService.getProcessName());
			sb.setNodeName(adminService.getNodeName());
			
	        
	        // Collection of all the resources
	        List<ResourceBean> resources = new ArrayList<ResourceBean>();
	        
			// lookup the Performance mbean 
			// note: PMI Stats must already be enabled
			ObjectName queryName = new ObjectName("WebSphere:node=" + sb.getNodeName() + ",process=" + sb.getServerName() + ",type=Perf,*");
			@SuppressWarnings("unchecked")
			Set<ObjectName> perfMBeans = adminService.queryNames(queryName, null);
						
			if (perfMBeans.isEmpty())
				throw new Exception ("No Performance MBean found!");
			
			if (perfMBeans.size() > 1)
				throw new Exception ("More than one performance MBean found!");

			// get the Performance MBean. 
			ObjectName perfMBean = (ObjectName) perfMBeans.iterator().next();
				
			// loop through the mbeans that are required
			List<ObjectName> allMbeans = new ArrayList<ObjectName>();
			
			// loop through the mbeans that are required			
			for (int k = 0; k < mbeansList.length; k++) {
				
				// Retrieve the specific mbean
				queryName = new ObjectName("WebSphere:node=" + sb.getNodeName() + ",process=" + sb.getServerName() + ",type=" + mbeansList[k] +  ",*");			
				@SuppressWarnings("unchecked")
				Set<ObjectName> mbeans = adminService.queryNames(queryName, null);
				
				// get the actual bean and place it in the array
				Iterator<ObjectName> it = mbeans.iterator();
				while (it.hasNext()) {
					allMbeans.add(it.next());
				}
			}
			
			// setup an array of ObjectNames and get the stats 
			// This is more effecient than looping through and retrieving the individual stats
			ObjectName[] mbeans = new ObjectName[allMbeans.size()];
			for (int i = 0; i < allMbeans.size(); i++ )
				mbeans[i] = (ObjectName)allMbeans.get(i);
									
				// Retrieve the stats for the mbean
				Object[] params = new Object[] {mbeans, new Boolean(false)};
				String[] signature = new String[] { "[Ljavax.management.ObjectName;", "java.lang.Boolean"};
		        WSStats[] stats  = (WSStats[])adminService.invoke(perfMBean, "getStatsArray", params, signature);	
	        
		        // get the PMI Configs (names, datatypes, etc)
		        PmiModuleConfig[] configs = (PmiModuleConfig[])adminService.invoke(perfMBean, "getConfigs", null, null);		
		        
		        //get the specific stats for each mbean
		        for (int j = 0 ; j < stats.length; j++) {
		        	
		        	// Make sure the stat does exist
		        	if (null == stats[j])
		        		continue;
		        	
					PmiModuleConfig config = PmiClient.findConfig(configs, stats[j].getName());
			        stats[j].setConfig(config); 
		        	
			        // get the PMI Stats
			        WSStatistic[] dataMembers = stats[j].getStatistics();
					ResourceBean rb = new ResourceBean();
			        rb.setResourceName(stats[j].getName());
					        	
			        if (dataMembers != null)  {
			        	for (int i=0; i< dataMembers.length; i++) {
			        		// For each data, cast it to be the real Statistic type so that we can get the value from each Statistic.
			        		// Can call the following common methods without knowing the data type:     
			        		// getName(), getDescription(), getStartTime(), getLastSampleTime()
			        	                  
			        		// check the data type and cast the data to the right type
			        		if (dataMembers[i] instanceof WSCountStatistic) {
			        			WSCountStatistic cs = (WSCountStatistic) dataMembers[i];
			        			rb.setPropertyName(cs.getName());
			        			rb.setDescription(cs.getDescription());
			        			rb.setCount(cs.getCount());
			        			rb.setTimestamp(cs.getLastSampleTime());
			        		} else if (dataMembers[i] instanceof WSTimeStatistic) {
			        			WSTimeStatistic ts = (WSTimeStatistic) dataMembers[i];
			        			rb.setPropertyName(ts.getName());
			        			rb.setDescription(ts.getDescription());
			        			rb.setMaxTime(ts.getMaxTime());
			        			rb.setMinTime(ts.getMinTime());     
			        			rb.setTimestamp(ts.getLastSampleTime());
			        		} else  if (dataMembers[i] instanceof WSRangeStatistic) {
			        			WSRangeStatistic rs = (WSRangeStatistic) dataMembers[i];
			        			rb.setPropertyName(rs.getName());
			        			rb.setDescription(rs.getDescription());	
			        			rb.setHighWaterMark(rs.getHighWaterMark());
			        			rb.setLowWaterMark(rs.getLowWaterMark());
			        			rb.setCurrentMark(rs.getCurrent());
			        			rb.setTimestamp(rs.getLastSampleTime());
			        		} // if	     	
			        	} // for
			        } // if
			        
			        resources.add(rb);
		        } // for
		        
		        // place the Vector of resources into the ServerBean
		        sb.setResourceBean(resources);
		        
		        // add it to the Distributed Object Cache
		        doc.put(sb.getServerName(), sb);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
