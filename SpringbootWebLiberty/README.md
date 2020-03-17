# SpringbootWebLiberty #

Small application and Maven POM showing how a Springboot application can be packaged as a war file and deployed to Liberty without the need for all the extraneous Springboot libraries.  When using the mvn package command two files will be in the target, a jar that contains the necessary Liberty installation and application server, and a war file that contains only the Springboot application.  The war can be dropped into the dropins folder of an existing Liberty application server.


