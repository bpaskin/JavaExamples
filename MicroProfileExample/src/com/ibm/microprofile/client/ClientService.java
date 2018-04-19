package com.ibm.microprofile.client;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.microprofile.model.Artist;

@Path("v1")
public interface ClientService {

	@GET
	@Path("artists")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Artist> getAllArtists();
}
