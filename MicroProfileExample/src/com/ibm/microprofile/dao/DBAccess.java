package com.ibm.microprofile.dao;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import com.ibm.microprofile.model.Artist;
import com.ibm.microprofile.model.Release;
import com.ibm.microprofile.model.ReleasePK;

@Stateless
public class DBAccess {
	private static String CLASSNAME = DBAccess.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);

	@PersistenceContext(unitName="MicroProfilePU")
	private EntityManager em;
	
	// place primeDb in jvm.options, server.env, bootstrap.properties or 
	// microprofile-config.properties
	@Inject @ConfigProperty(name="primeDb")
	private String[] primeDb;
	
	@Timeout(unit=ChronoUnit.MILLIS, value=500) 
	@Retry(maxRetries=2, durationUnit=ChronoUnit.MILLIS, maxDuration=500, jitterDelayUnit=ChronoUnit.MILLIS, jitter=200)
	public List getAll() {
		LOGGER.entering(CLASSNAME, "getAll");
		return em.createNamedQuery("Artist.findAll").setHint("javax.persistence.query.timeout", 200).getResultList(); 
	}
	
	@Timeout(unit=ChronoUnit.MILLIS, value=500)
	public List getAllArtist(String artist) {
		LOGGER.entering(CLASSNAME, "getAllArtist", artist);
		return em.createNamedQuery("Release.findAllByArtist").setParameter("artist", artist).setMaxResults(50).setHint("javax.persistence.query.timeout", 1000).getResultList(); 
	}
	
	public void addArtist(String name) {
		LOGGER.entering(CLASSNAME, "addArtist", name);
		Artist artist = new Artist();
		artist.setName(name);
		em.persist(artist);
		LOGGER.exiting(CLASSNAME, "addArtist");
	}
	
	@CircuitBreaker(successThreshold=10, requestVolumeThreshold=4, failureRatio=0.75, delay=1000, delayUnit=ChronoUnit.MILLIS)
	public void addRelease(String artist, String release, int year) {
		LOGGER.entering(CLASSNAME, "addRelease", artist + " " + release + " " + year);
		ReleasePK releasePK = new ReleasePK();
		releasePK.setArtist(artist);
		releasePK.setName(release);
		
		Release releaseBean = new Release();
		releaseBean.setId(releasePK);
		releaseBean.setYear(year);
		
		Artist artistBean = new Artist();
		artistBean.setName(artist);
		releaseBean.setArtistBean(artistBean);
		
		em.persist(releaseBean);
		LOGGER.exiting(CLASSNAME, "addRelease");
	}
	
	public void primeDB() {
		LOGGER.entering(CLASSNAME, "primeDB");
		LOGGER.finer("Number of release: " + primeDb.length);
		
		for (int i = 0; i < primeDb.length; i++) {
			LOGGER.finer("Before splitting release");
			String[] release = primeDb[i].split(">");
			LOGGER.finer(release[0] + " " + release[1] + " " + Integer.parseInt(release[2]));
			addArtist(release[0]);
			addRelease(release[0], release[1], Integer.parseInt(release[2]));
		}
		LOGGER.exiting(CLASSNAME, "primeDB");
	} 
}
