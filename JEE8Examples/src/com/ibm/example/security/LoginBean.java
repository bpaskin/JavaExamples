package com.ibm.example.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RequestScoped
@Named
@FacesConfig(version=FacesConfig.Version.JSF_2_3)
public class LoginBean implements Serializable {
	private static final long serialVersionUID = -738199602589074911L;

	@Inject
	private SecurityContext securityContext;
	 
	@Inject
	private FacesContext facesContext;
	 
	@NotNull
	@NotBlank
	@Size(min=2, message="username must be at least 2 characters in length")
	private String username;
	   
	@NotNull
	@NotBlank
	@Size(min=2, max=10, message="password must be between 2 and 8 characters in length")
	private String password;
	   
	public void login() {
		   Credential credentials = new UsernamePasswordCredential(username, new Password(password));
		   AuthenticationStatus status = securityContext.authenticate(
				   (HttpServletRequest) facesContext.getExternalContext().getRequest(),
				   (HttpServletResponse) facesContext.getExternalContext().getResponse(),
				   AuthenticationParameters.withParams().credential(credentials)
			);
		   
	       if (status.equals(AuthenticationStatus.SEND_CONTINUE) || status.equals(AuthenticationStatus.SUCCESS)) {
	           facesContext.responseComplete();
	       } 
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
