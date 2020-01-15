This is a simple application that allows application servers and servlet engines to use Apache CXF as their web services engine.  This example exposes one web service.  Unlike application servers that discover the web services endpoints, this must be done manually, which is done in the `com.ibm.jaxws.Configuration` servlet.

Access to the web services is through http://host:port/uri .  Example http://localhost:9080/CXFExample
