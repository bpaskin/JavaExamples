package com.ibm.microprofile.services;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

import com.ibm.microprofile.dao.DBAccess;
import com.ibm.microprofile.model.Artist;
import com.ibm.microprofile.model.Release;

@RequestScoped
@Path("v1") 
public class CompactDiscs {	
	private static String CLASSNAME = CompactDiscs.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
	
	@Inject
	private DBAccess dao;
		
	@Counted(displayName="getAllArtistsCount", description = "Counting number of List of Artists", absolute = true)
	@Timed(name="getAllArtistsTimer", description = "Timing of the getAllArtists",absolute=true)
	@Metered(absolute = true, name = "getAllArtistsMeter")
	@Bulkhead(value=5, waitingTaskQueue=5)
	@Path("artists") @GET @Produces(MediaType.APPLICATION_JSON)
	public List<Artist> getAllArtists() throws WebApplicationException {
		LOGGER.entering(CLASSNAME, "getAllArtists");
		try {
			return dao.getAll();
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "getAllArtists", e);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}
	
	@Bulkhead(value=5, waitingTaskQueue=5)
	@Path("{artist}") @GET @Produces(MediaType.APPLICATION_JSON)
	public List<Release> getAllReleases(@PathParam("artist") String artist) throws WebApplicationException {
		LOGGER.entering(CLASSNAME, "getAllReleases", artist);
		try {
			return dao.getAllArtist(artist);
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "getAllReleases", e);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		} 
	}
	
	@Bulkhead(value=5, waitingTaskQueue=5)
	@Path("add/{artist}/{release}/{year}") @GET @Produces(MediaType.APPLICATION_JSON)
	public Response addRelease(@PathParam("artist") String artist, @PathParam("release") String release, @PathParam("year") int year) throws WebApplicationException {
		LOGGER.entering(CLASSNAME, "addRelease", artist + " " + release + " " + year);
		try {
			dao.addRelease(artist, release, year);
			LOGGER.exiting(CLASSNAME, "addRelease");
			return Response.ok().build();
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "addRelease", e);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		} 
	}
	
	@Bulkhead(value=5, waitingTaskQueue=5)
	@Path("add/{artist}") @GET @Produces(MediaType.APPLICATION_JSON)
	public Response addArtist(@PathParam("artist") String artist) throws WebApplicationException {
		LOGGER.entering(CLASSNAME, "addArtist", artist);
		try {
			dao.addArtist(artist);
			LOGGER.exiting(CLASSNAME, "addArtist");
			return Response.ok().build();
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "addArtist", e);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		} 
	}	
	
	@Bulkhead(value=1)
	@Path("prime") @GET @Produces(MediaType.APPLICATION_JSON)
	public Response prime() throws WebApplicationException {
		LOGGER.entering(CLASSNAME, "prime");
		try {
			dao.primeDB();
			LOGGER.exiting(CLASSNAME, "prime");
			return Response.ok().build();
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "prime", e);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		} 
	}	
}
