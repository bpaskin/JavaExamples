package com.ibm.example.vertx;

/**
 * Small example of using the Deployment Options to set
 * configuration and then retrieve the options later
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 *
 */

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class ConfigHardcoded extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHardcoded.class);

	public static void main(String[] args) {

		// Set configuration
		DeploymentOptions options = new DeploymentOptions();
		options.setConfig(new JsonObject().put("http.port", 9080));

		Vertx vertx = Vertx.vertx();

		// pass the configuration with the new verticle
		vertx.deployVerticle(new ConfigHardcoded(), options);
	}

	@Override
	public void start() {
		LOGGER.info("ConfigHardcoded Started");

		Router router = Router.router(vertx);
		router.route().handler(StaticHandler.create().setCachingEnabled(false));

		// setup the http server and use the config HTTP Port.
		vertx.createHttpServer().requestHandler(routingContext -> {
			routingContext.response().end("<html><body><h1>Welcome to the Configuration</h1></body></html>");
		}).listen(config().getInteger("http.port"));
	}

	@Override
	public void stop() {
		LOGGER.info("ConfigHardcoded Stopped");
	}
}
