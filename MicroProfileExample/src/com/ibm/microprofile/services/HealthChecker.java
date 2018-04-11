package com.ibm.microprofile.services;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class HealthChecker implements HealthCheck {
	private static String CLASSNAME = HealthChecker.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);

	
	@Inject @ConfigProperty(name="getAllArtists")
	private String getAllArtistsURL;
	
	Client client = ClientBuilder.newClient();

	@Override
	public HealthCheckResponse call() {
		LOGGER.entering(CLASSNAME, "call");
		HealthCheckResponseBuilder artists = HealthCheckResponse.named("getAllArtists");		
		return artists.withData("/getAllArtists", "Get a list of artists").state(checkAllArtists()).build();
	}
	
	private boolean checkAllArtists() {
		LOGGER.entering(CLASSNAME, "checkAllArtists");
		try {
			Response response = client.target(getAllArtistsURL).request(MediaType.APPLICATION_JSON).get(Response.class);
			LOGGER.finer("Status code of call: " + response.getStatusInfo().getStatusCode());
		
			if (response.getStatusInfo().getStatusCode() == Status.OK.getStatusCode()) {
				LOGGER.exiting(CLASSNAME, "checkAllArtists", true);
				return true;				
			} else {
				LOGGER.exiting(CLASSNAME, "checkAllArtists", false);
				return false;
			}
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "checkAllArtists", e);
			return false;
		}
	}
}
