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

		var dbs = connection.listDatabaseNames();
		LOGGER.finer("Received DB List");

		var dbList = new ArrayList<String>();

		for (var s : dbs) {
			dbList.add(s);
			LOGGER.finer("Found DB: " + s);
		}

		LOGGER.exiting(this.getClass().getCanonicalName(), "getDBNames");
		return dbList;
	}

}
