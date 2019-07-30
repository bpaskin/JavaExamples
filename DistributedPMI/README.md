# Distributed PMI

Allows for PMI statistics to be gathered from tWAS Application Servers, store them in a Distributed Cache, and display the results using a Servlet.  PMI is a feature that requires administrative permissions.  This code is older and could be cleaned up and brought up to newer Java / JEE standards.

[PMI Statistics](https://www.ibm.com/support/knowledgecenter/en/SSAW57_9.0.5/com.ibm.websphere.nd.multiplatform.doc/ae/cprf_pmidata.html) that will be used should be turned on.  A shared [Object Cache](https://www.ibm.com/support/knowledgecenter/en/SSAW57_9.0.5/com.ibm.websphere.nd.multiplatform.doc/ae/tdyn_distmap.html) and should be in the [Replication Domain](https://www.ibm.com/support/knowledgecenter/en/SSAW57_9.0.5/com.ibm.websphere.nd.multiplatform.doc/ae/trun_drs_migrate.html).  The JNDI name of the Object Cache in the code is `cache/PMIStats`.  When the application is deployed the role of administrator needs to be given to the `PMIAdministrator` role.  

The `PMIStats` Singleton EJB is using a non persistent EJB timer that is triggered every 15 seconds.  This can be changed to be triggered how often necessary by updating `	@Schedule(second="*/15", hour="*", minute="*", persistent=false)`.  The [MBeans](https://www.ibm.com/support/knowledgecenter/en/SSAW57_9.0.5/com.ibm.websphere.javadoc.doc/web/apidocs/com/ibm/websphere/pmi/stat/package-summary.html) that are being looked at are `private static String[] mbeansList = {"ThreadPool", "ConnectionPool"};` and can be added to or changed.  

The Servlet included will retrieve the Objects from the Distributed Cache and display the results.
