## SFTP Status Check ###

This is a small [Quarkus](https://quarkus.io) application that I put together for a customer under 1 hour to be used as a availability check for a load balancer.  The application will trigger every x seconds and download a file from an SFTP site.  If the file transfer is successful, then the status is marked `UP` otherwise,  `DOWN`.  There is another status, `MAINTENANCE`, which is used to mark the server is maintenance mode.  REST call can be used to retrieve the status and move in and out of maintenance mode.  

A configuration file, `application.properties` is provided in the resources directory, but can be overridden by having a `config` directory with the file in it in the same directory as the application.  The configuration file has logging, SFTP, user, and file information.  The password is in an encrypted form.  There is a REST endpoint to help encrypt passwords.  The password is decrypted by the application.  The salt, key password, iterations, etc are suggested to be changed.  The encryption/decryption was borrowed from some Internet posts dealing with encrypting passwords in configuration files.

The application was compiled with [GraalVM](https://www.graalvm.org) to make a native image.  

Endpoints:

Encrypt password:
http://host:1080/encrypt/{password}

Enter maintenance mode:
http://host:1080/mainton

Exit maintenance mode:
http://host:1080/maintoff

Status (any other endpoint):
http://host:1080/
