package com.ibm.example.security;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


// Can choose one Authentication Mechanism at a time.

//@BasicAuthenticationMechanismDefinition(realmName="secured realm")

@FormAuthenticationMechanismDefinition(
	loginToContinue = @LoginToContinue(loginPage = "/login.jsp",
		errorPage = "/loginError.jsp",
		useForwardToLogin=true)
)

//@CustomFormAuthenticationMechanismDefinition(
//	loginToContinue = @LoginToContinue(loginPage = "/loginCustom.xhtml",
//			errorPage = "/loginError.jsp"
//	)
//)

// ----------------------------------------------

// Use an Identity Store and not the method built into the App Server
// Overrides what is in the App Server
//@DatabaseIdentityStoreDefinition(
//		dataSourceLookup = "${'jdbc/users'}",
//		callerQuery = "#{'SELECT password FROM users WHERE username = ?'}",
//		groupsQuery = "SELECT groupname FROM groups WHERE username = ?",
//		hashAlgorithm = PasswordHash.class,
//		priority = 10
//)

//@LdapIdentityStoreDefinition(
//		url = "ldap://hostname:389/",
//		callerBaseDn = "ou=caller,dc=jsr375,dc=net",
//		groupSearchBase = "ou=group,dc=jsr375,dc=net"
//)

@Named
@ApplicationScoped
public class Config {
}
