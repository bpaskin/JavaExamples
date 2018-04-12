package com.ibm.microprofile.services;

import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorResponseMapper implements ExceptionMapper<WebApplicationException> {
	private static String CLASSNAME = ErrorResponseMapper.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
	
	@Override
	public Response toResponse(WebApplicationException ex) {
		LOGGER.entering(CLASSNAME, "toResponse", ex.getMessage());
		Error error = new Error();
		error.setStatus(ex.getResponse().getStatus());
		error.setMessage("Error processing your request: " + ex.getMessage());
		LOGGER.exiting(CLASSNAME, "toResponse");
		return Response.serverError().type(MediaType.APPLICATION_JSON).entity(error).build();
	}
	
	public class Error {
		public int Status;
		public String message;
		public int getStatus() {
			return Status;
		}
		public void setStatus(int status) {
			Status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
