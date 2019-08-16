package com.ibm.example.enterprise;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class MongoDAO {

	private Logger LOGGER = Logger.getLogger(this.getClass().getCanonicalName());

	@EJB
	private ConnectionProvider client;

	public List<String> getDBNames() throws Exception {
		LOGGER.entering(this.getClass().getCanonicalName(), "getDBNames");

		if (null == client) {
			throw new Exception("ConnectionProvider is null");
		}

		var connection = client.getConnection();
		LOGGER.finer("Have DB connection");

		var dbList = new ArrayList<String>();

		connection.listDatabaseNames().into(dbList);
		LOGGER.finer("Received DB List");

		LOGGER.exiting(this.getClass().getCanonicalName(), "getDBNames");
		return dbList;
	}

}
