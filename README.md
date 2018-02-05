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

For this example the endpoints for the RESTful services are http://host:port/SwaggerExample/services .  The Swagger JSON is found at http://host:port/SwaggerExample/services/swagger.json .  Finally, the Swagger UI can be found at http://host:port/SwaggerExample/swagger-ui .

Another solution would be to download the Swagger UI and have a servlet generate the index.html on load or duplicate its functionality.

If this is going to be run in Liberty, the application needs to be exanded using: ``<applicationManager autoExpand="true"/>`` in the server.xml.

# SpringBootSwagger
This is an example similar to the Swagger Example, but using Spring Boot and SpringFox to integrate with Swagger and Swagger UI.  Like the previous example, both Swagger annotated and non Swagger annotated classes work with Swagger.  The classes does not use JAX-RS, but the Spring specific annotations for RESTful services.  The Spring UI can be found at http://host:port/SpringBootSwagger/swagger-ui.html .  The Swagger JSON can be found at http://host:port/SpringBootSwagger/v2/api-docs .
