# JEE vs Spring Boot code and performance

I put a few samples together of JEE and Spring code that do the same things.  Spring does some nice things out of the box, so I duplicated that in some of the JEE examples to boost performance. 

The tests were run inside an IBM Liberty 18.0.0.3 App Server using IBM JRE 8.0.5.22 using Ubuntu Linux 18.04.1 LTS with MySQL 5.7.24 with the MySQL 8.0.13 JDBC driver.  The machine is a home built server using an AMD 2700x Ryzen processor with 32 GB of memory at 2600 GHz.  Spring Boot applications were tested inside Liberty and running standalone (Tomcat) using the same IBM JRE.  The `liberty` directory inside each project has the setup.  Each JVM was given 1 GB of memory to run and added the necessary features in the server.xml of Liberty.  Each application would start on the same port, 9080, and was given the same context root, to make it easier for testing.  

Testing was done using JMeter headless.  The script can be found in the `jmeter` directory.  I created a small script that would call the JMeter script 10 times, gather results, and print out the average result in milliseconds.  There is a 20 second pause between iterations.  The script runs 25 threads asynchronously and adds a user, called "Brian", gets the user, gets all users and then finally clears the entries.

# Thoughts
- Spring does save time coding in some areas, but not as much as I would expect.  On a larger project it could save much more time coding with Spring.
- There is currently nothing like Spring MVC in JEE.  JEE has JSF.  That is request based vs component based discussion.
- When I added EclipseLink as the JPA provider in Spring, instead of the default Hibernate, I had to change the value of Entity field to Integer from int.  I am not sure why, since it works as int with Hibernate and as int within Liberty, which uses EclipseLink.
- Running an application as a Spring Boot application solo using Tomcat, is slower than when using the applications within Liberty.
- Spring usually does make good choices for performance, but those advantages disappear when added to JEE applications.

# Results
Simple REST calls

| Platform | Description | Time
| --- | --- | --- |
|Liberty|perfRESTJEE|3631 ms|
|Liberty|perfRESTSpring|2909 ms|
|SpringBoot|perfRESTSpring|3064 ms|

REST with JPA

| Platform | Description | Time
| --- | --- | --- |
|Liberty|perfRESTJPAJEE|65531 ms|
|Liberty|perfRESTJPASpring|37076 ms|
|SpringBoot|perfRESTJPASpring|37757 ms|
|Liberty|perfRESTJPAJEE-EMF|2310 ms **|

REST with JPA (EclipseLink)

| Platform | Description | Time
| --- | --- | --- |
|Liberty|perfRESTJPASpring-EL|37709 ms|
|SpringBoot|perfRESTJPASpring-EL|76791 ms|

** = Static EntityManagerFactory