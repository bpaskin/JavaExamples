package com.ibm.microprofile.services;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import com.ibm.microprofile.client.ClientService;
import com.ibm.microprofile.model.Artist;

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
			// using MP Client
			URL apiUrl = new URL(getAllArtistsURL);
			ClientService client = RestClientBuilder.newBuilder().baseUrl(apiUrl).build(ClientService.class);
			List<Artist> artist = client.getAllArtists();
			
			if (null == artist) {
				LOGGER.exiting(CLASSNAME, "checkAllArtists", false);
				return false;
			} else {
				LOGGER.exiting(CLASSNAME, "checkAllArtists", true);
				return true;
			}
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "checkAllArtists", e);
			return false;
		}
	}
}
