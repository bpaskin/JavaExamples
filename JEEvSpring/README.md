# JEE vs Spring Boot code and performance

I put a few samples together of JEE and Spring code that do the same things.  Spring does some nice things out of the box, so I duplicated that in some of the JEE examples to boost performance. 

The tests were run inside an IBM Liberty 19.0.0.2 App Server using IBM JRE 8.0.5.30 and OpenJDK 11.0.2+9 with OpenJ9 0.12.1 using Ubuntu Linux 18.04.2 LTS with MySQL 5.7.25 with the MySQL 8.0.13 JDBC driver.  The machine is a home built server using an AMD 2700x Ryzen processor with 32 GB of memory at 2600 GHz.  Spring Boot applications were tested inside Liberty and running standalone (Tomcat) using the same JREs.  The `liberty` directory inside each project has the setup.  Each JVM was given 1 GB of memory to run and added the necessary features in the server.xml of Liberty.  Each application would start on the same port, 9080, and was given the same context root, to make it easier for testing.  

Testing was done using JMeter headless.  The script can be found in the `jmeter` directory.  I created a small script that would call the JMeter script 10 times, gather results, and print out the average result in milliseconds.  There is a 20 second pause between iterations.  The script runs 25 threads asynchronously and adds a user, called "Brian", gets the user, gets all users and then finally clears the entries.

Sample of starting the SpringBoot applications:

```/opt/IBM/WebSphere/wlp/java/ibm-java-x86_64-80/jre/bin/java -Xms1024m -Xmx1024m -Dserver.port=9080 -Dserver.servlet.context-path=/PerfRESTJEE -jar /opt/IBM/WebSphere/wlp/usr/servers/perfRESTJPASpring/apps/PerfRESTJPASpring.jar```

# Thoughts
- Spring saving time in programming is negligible.
- There is currently nothing like Spring MVC in JEE.  JEE has JSF.  That is request based vs component based discussion.
- When I added EclipseLink as the JPA provider in Spring, instead of the default Hibernate, I had to change the value of Entity field to Integer from int.  I am not sure why, since it works as int with Hibernate and as int within Liberty, which uses EclipseLink.
- SpringBoot 2.1.3 with both Java 8 and 11 have improved performance, which shows that they are better now, whereas JEE code ran equal to or a bit slower, especially under Java 11.

*UPDATE 05/11/2018* - I changed the lock strategy on the Named Queries on all and the results reflect the change.

*UPDATE 20/03/2019* - Remove comment about Spring and @Transactional after speaking with Oliver D on twitter.  He has provided some insights and the code will need to be modified in the future for testing.

*UPDATE 27/03/2019* - Added @Transactional annotation on the interface methods, and added DB pooling parameters for Spring JPA.

*UPDATE 28/03/2019* - Updated POMs, ran tests on new Java, Liberty, and Spring versions.
Liberty 18.0.0.3 -> 19.0.0.2 ND
IBM JRE 8.0.5.22 -> IBM JRE 8.0.5.30 & OpenJDK 11.0.2+9 with OpenJ9 0.12.1
SpringBoot 2.0.5 -> 2.1.3
EclipseLink 2.7.3 -> 2.7.4

# Results
Simple REST calls

| Platform | Description | Time JRE 80522 | Time JRE 80530 | Time JRE 1102+9 | 
| --- | --- | --- | --- | --- |
|Liberty 18003|perfRESTJEE|3631 ms|||
|Liberty 18003|perfRESTSpring|2909 ms|||
|SpringBoot 205|perfRESTSpring|3064 ms|||
|Liberty 19002|perfRESTJEE||3473ms|6340 ms|
|Liberty 19002|perfRESTSpring||2833 ms|2875 ms|
|SpringBoot 213|perfRESTSpring||2759 ms|2874 ms|

REST with JPA

| Platform | Description | Time JRE 80522 | Time JRE 80530 | Time JRE 1102+9 | 
| --- | --- | --- | --- | --- |
|Liberty 18003|perfRESTJPAJEE|32605 ms|||
|Liberty 18003|perfRESTJPASpring|34864 ms|||
|SpringBoot 205|perfRESTJPASpring|34694 ms|||
|Liberty 19002|perfRESTJPAJEE||33335 ms|33455 ms|
|Liberty 19002|perfRESTJPASpring||33334 ms|33081 ms|
|SpringBoot 213|perfRESTJPASpring||32777 ms|32917 ms|

REST with JPA (EclipseLink)

| Platform | Description | Time JRE 80522 | Time JRE 80530 | Time JRE 1102+9 | 
| --- | --- | --- | --- | --- |
|Liberty 18003|perfRESTJPASpring-EL|36181 ms|||
|SpringBoot 205|perfRESTJPASpring-EL|78207 ms|||
|Liberty 19002|perfRESTJPASpring-EL||37361 ms|40332 ms|
|SpringBoot 213|perfRESTJPASpring-EL||81922 ms|78695 ms|

- perfRESTJEE - REST calls using JEE
- perfRESTSpring - REST calls using Spring Boot
- perfRESTJPAJEE - REST calls with JPA using JEE
- perfRESTJPASpring - REST calls with Hibernate JPA using Spring Boot
- perfRESTJPASpring-EL - REST calls with EclipseLink JPA using Spring Boot
- perfRESTJPADSSpring - REST calls with Hibernate JPA using JEE DataSource using Spring Boot
