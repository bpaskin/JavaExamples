package com.ibm.example.enterprise;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

@Singleton
public class ConnectionProvider {

	private Logger LOGGER = Logger.getLogger(this.getClass().getCanonicalName());

	private String connectString = "mongodb://192.168.1.20:27017";
	private MongoClient client = null;

	public synchronized MongoClient getConnection() {
		LOGGER.entering(this.getClass().getCanonicalName(), "getConnection");

		if (null == client) {
			this.createConnection();
		}

		LOGGER.exiting(this.getClass().getCanonicalName(), "getConnection");
		return client;
	}

	@PostConstruct
	private void createConnection() {
		LOGGER.entering(this.getClass().getCanonicalName(), "createConnection");

		try {
			MongoClientOptions.Builder options = MongoClientOptions
					.builder()
					.sslEnabled(true)
					.connectionsPerHost(25)
					.connectTimeout(3000)
					.maxConnectionIdleTime(300000);
			LOGGER.finer("MongoClientOptions are set");

			MongoClientURI uri = new MongoClientURI(connectString, options);
			LOGGER.finer("MongoClientURI is set");

			client = new MongoClient(uri);
			LOGGER.finer("Setting MongoClient");

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		LOGGER.exiting(this.getClass().getCanonicalName(), "createConnection");
	}
}
