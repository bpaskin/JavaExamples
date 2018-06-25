-------------------------------------------------
Two Factor One Time Password with Google Authenticator v1.0

Brian S Paskin (bpaskin@us.ibm.com)
30 Nov 2015
Updated 25/06/2018
-------------------------------------------------

This shows a how to incorporate TFOTP into your applications.

TwoFactorAuthJAAS.jar - Sample WebSphere Trust Association Interceptor (TAI).  Currently it is setup to use a database for authenticate users.
TwoFactorAuth.web - Sample application uses LoginModule and logon to an application and check Google Authenticator Code.

This will work in both Liberty Profile and Traditional WebSphere. Tested on v8.5.5.4 - v8.5.5.13

TwoFactorAuthJAAS.jar must installed in the classpath of the App Server.  I used a shared library in Liberty.
The application also relies on Apache Commons Codec for Base64 implementation.  This must be included in the shared library.

Server security must be setup.
Datasource must be setup.
JAAS must be setup.
Security Role for Application must be setup.

See included server.xml for example setup.  
See dbsetup_mysql.ddl for MySQL DDL

Include the TwoFactorAuthJAAS.jar in the <server>/lib directory.
Include the TwoFactorAuth.web in the <server>/apps directory.

    