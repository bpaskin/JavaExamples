# JavaExamples

# JSFParams
Small application tested on tWASv9 and Liberty (jsf-2.2 feature) that has 3 pages.  the index page the user enters a name and it is passed to the backing bean.  Page 2 and Page 3 read the backing bean from the HttpSession.

# CacheTest
Application to test the Object Cache in both tWAS and Liberty. 

For Liberty Add the following features:
``  <featureManager>
        <feature>distributedMap-1.0</feature>
        <feature>ejbLite-3.1</feature>
        <feature>jsf-2.0</feature>
        <feature>servlet-3.1</feature>
    </featureManager>``
 
 And add the following cache:
`` <distributedMap id="cache" jndiName="cache/test"/> ``
 
 For tWAS:
 1. Optional: Create replication domain, if going to be used across servers.  (Environment > Replication Domains > New...)
 2. Create a new Object Cache and link Replication Domain, if using it.  (Resources > Cache Instances > Object Cache Instances > New...).  The JNDI name should be called "cache/test".  Make sure the cache is created at the correct scope.
 
# MQTester
Small application that allows user to input the JNDI of a Queue Connection Factory and Queue name and PUT, GET, or PUT and GET a messgae from the Queue entered. 

# SwaggerExample
Liberty has a great feature called [apiDiscovery](https://www.ibm.com/support/knowledgecenter/en/SSAW57_liberty/com.ibm.websphere.wlp.nd.multiplatform.doc/ae/twlp_api_discovery.html) that uses Swagger and Swagger UI under the covers to document RESTful API endpoints for all applications deployed to the application server.  I would recommend this feature rather than create your own.

If you want to create your own this example shows you how.  It allows for both Swagger API annotated and regular JAX-RS classes without Swagger annotations to be documented by the Swagger API.  The difficult part is the Swagger UI.  It can be [downloaded](https://github.com/swagger-api/swagger-ui) and added to the project, while changing the index.html page to suite the needs of the application.  In this example the POM downloads the compressed tarball, uncompresses it, places it in the appropriate directory and then updates the default URL to the one for this project.

# JEEvSpring
Added some examples of JEE and Spring Boot code that do the same things.  I also ran performance numbers on them.  Tested were mostly REST, JPA and CDI.
# AppSecurityExample
A sample of several best practices when securing JEE applications.  Passwords are not encoded for example purposes only.  Normally they would be encoded and kept somewhere safe and not hard coded in the application.
For this example the endpoints for the RESTful services are http://host:port/SwaggerExample/services .  The Swagger JSON is found at http://host:port/SwaggerExample/services/swagger.json .  Finally, the Swagger UI can be found at http://host:port/SwaggerExample/swagger-ui .

Another solution would be to download the Swagger UI and have a servlet generate the index.html on load or duplicate its functionality.

If this is going to be run in Liberty, the application needs to be exanded using: ``<applicationManager autoExpand="true"/>`` in the server.xml.

# SpringBootSwagger
This is an example similar to the Swagger Example, but using Spring Boot and SpringFox to integrate with Swagger and Swagger UI.  Like the previous example, both Swagger annotated and non Swagger annotated classes work with Swagger.  The classes does not use JAX-RS, but the Spring specific annotations for RESTful services.  The Spring UI can be found at http://host:port/SpringBootSwagger/swagger-ui.html .  The Swagger JSON can be found at http://host:port/SpringBootSwagger/v2/api-docs .

If this is going to be run in Liberty, the application needs to be exanded using: ``<applicationManager autoExpand="true"/>`` in the server.xml.

# Datasource Tester
This is a small application that grabs a connection based on JDBC JNDI name and gets the Database information and tests the connection by receiving a list of tables.  Receiving the list of tables is necessary to actually test the connection since the connections in the datasource pool may be stale.  There is a single page that requires the input of the datasource JNDI name. Errors are written to System Error log. This was tested on tWASv8.5.5.x and Liberty.

25/06/2019 - Updated to accept a JAX-RS POST request 
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d 'jndi/name/goes/here' http://host:port/DatasourceTester/rest/jndi

# MicroProfileExample
Using MicroProfile 1.3 and the features Config, Fault Tolerance, Health Check, Metrics, Rest Client, and OpenAPI.  Just a small sample of how to use these features together.

# TwoFactorOneTimePassword
Two Factor One Time Password with Google Authenticator for Liberty and tWAS.  This is a LoginModule and sample application that secures an applications using Google Authenticator.  Sample setup with server.xml.  Requires a database to store the Google Authenticator codes.  

# WASListenerPortCheck
For tWASv855 and later.  This is older code that I adapted to use EJB timers, generics, and exception handling in Java 8.  This runs every 30 seconds and checks the Listener Ports.  If they are stopped, they are automatically started.  Must map LPAdmin security role to user or group that has administrative privledges.

# JEE8Examples
Some examples of major updates to JEE8.  Not all features of JEE8 implemented.

# JythonLiberty
I wrote an [article](https://www.ibm.com/developerworks/websphere/techjournal/1201_paskin/1201_paskin.html) a few years ago about adding Jython to tWAS. This shows how to add Jython to Liberty and use the both PyServlet, use the Interpreter and import modules.  I decided against using the web.xml and made a simple Servlet to do the intercept of the Jython servlets.

The [standalone Jython](https://mvnrepository.com/artifact/org.python/jython-standalone) needs to be downloaded and placed in the wlp/usr/shared/resources directory and referenced in the Liberty server.xml. 

in the jvm.options file required is the 

`-Dpython.home=/path/to/wlp/usr/shared/resources/jython-standalone-x.x.x.jar`

optionally adding python.path can import other modules.  The paths can be separated by a colon (:)

`-Dpython.path=/path/to/library1:/path/to/library2`

Jython servlets should be placed in the WebContent directory.

# CDI2.0Examples
CDI 2.0 examples including the Liberty server.xml.  Covers the diverse patterns and uses.  

# MongoDBConnect
A Mongo DB connection test that uses SSL.  Enter the userid, pw, database, server and port and connect.  Requries the MongoDB server public certificate in the truststore.

# LibertyCheckCompliance
A Java program that can read in a Liberty server.xml (or any other XML files) and use Bean Validation 2.0 to validate the entries.  This is good to check that all Liberty servers are following the standards set forth by an organization.

# SpringBootOnWAS
An simple example of using JAX RS with tWAS v855 that basically excludes the Boot part of Spring to work under tWAS and JEE6.

# SimpleSecurity
A basic example of using security constraints to protect pages/servlets.  In Liberty I am allowing all user that are authenticated to see the secured page, and setting up a user registry.  This will work with LDAP, OIDC, in the same manner, only the setup in Liberty or other app server would need to be changed.

# EventStreamsWithKafka
Samples for Kafka with Producer, Consumer and Streams.  Can be used with IBM Cloud Event Streams by changing necessary parameters in the bootstrap.properties files.  Example with Open Liberty or Liberty.  Uses MicroProfile Config to inject parameters.  Producer is accessed through REST POST call to lookup search results on Google and return them.  Stream will filter them based on if "https" is used.

# MQStream
This is an example of an MQ/JMS Stream similar to that of Kafka.  Takes a REST POST, searches Google, sends the results to a JMS Queue, in this MQ, and an MDB picks up the message processes it through a MicroProfile Reactive Stream, and then sends those that have an 'https' in the URL are sent to the output Queue.  The Consumer then consumes those messages via an MDB and places the results in System.out.

# PubSubStreamJSE11
A Java SE 11 example of using the [Stream](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/stream/Stream.html) API, and [Flow.Publisher](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/Flow.Publisher.html) and [Flow.Subscriber](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/Flow.Subscriber.html) APIs, which replace the [Oberservable](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Observable.html) and [Observer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Observer.html) APIs. No third party libraries required.  


