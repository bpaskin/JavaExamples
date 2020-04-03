package com.ibm.example.vertx;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MongoApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoApp.class);

	public static void main(String[] args) {
		LOGGER.info("Starting App ...");

		Vertx vertx = Vertx.vertx();

		// Read the config file
		ConfigRetriever config = ConfigRetriever.create(vertx);

		// deploy the verticles. The first verticle is a regular verticle and will
		// handle the
		// web requests. The second verticle is a worker verticle and will handle
		// the mongodb interaction. The verticles will be connected using the bus.
		config.getConfig(json -> {

			// make sure the json is read correctly
			if (json.succeeded()) {
				LOGGER.info("Configuration file read successfully");
				JsonObject configurations = json.result();
				DeploymentOptions options = new DeploymentOptions().setConfig(configurations);

				// deploy verticles
				// This is the main verticle to handle the requests
				vertx.deployVerticle(MongoVerticle1.class.getName(), options, res -> {
					if (res.succeeded()) {
						LOGGER.info(MongoVerticle1.class.getName() + " deployed");
					} else {
						LOGGER.info(MongoVerticle1.class.getName() + " failed deployment");
						System.exit(1);
					}
				});

				// This is the mongodb worker verticle. It will launch 10 instances and 10
				// worker threads
				vertx.deployVerticle(MongoVerticle2.class.getName(),
						options.setWorker(true).setWorkerPoolName("mongo-pool").setWorkerPoolSize(10).setInstances(10),
						res -> {
							if (res.succeeded()) {
								LOGGER.info(MongoVerticle2.class.getName() + " deployed");
							} else {
								LOGGER.info(MongoVerticle2.class.getName() + "  failed deployment");
								System.exit(1);
							}
						});

				// reading of the file failed
			} else {
				LOGGER.error("Error reading configuration file : " + json.cause().getMessage());
				System.exit(1);
			}
		});
	}
}