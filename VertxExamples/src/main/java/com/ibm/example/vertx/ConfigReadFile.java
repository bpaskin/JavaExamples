package com.ibm.example.vertx;

/**
 * Small example reading the config file
 * under resources > conf > config.json
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 *
 */
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class ConfigReadFile extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReadFile.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		// Read the config file
		ConfigRetriever config = ConfigRetriever.create(vertx);

		config.getConfig(json -> {
			if (json.succeeded()) {

				// set the configurations from the json results of the file
				JsonObject configurations = json.result();

				// set the deployment options using the results returned from the file
				DeploymentOptions options = new DeploymentOptions().setConfig(configurations);

				// deploy the verticle with the deployment options
				vertx.deployVerticle(new ConfigReadFile(), options);
			}

			if (json.failed()) {
				LOGGER.error("Failed to load configuration file");
				System.exit(1);
			}
		});
	}

	@Override
	public void start() {
		LOGGER.info("ConfigReadFile Started");

		Router router = Router.router(vertx);
		router.route().handler(StaticHandler.create().setCachingEnabled(false));

		// setup the http server and use the config HTTP Port. Set default port if
		// config not found.
		vertx.createHttpServer().requestHandler(routingContext -> {
			routingContext.response().end("<html><body><h1>Welcome to the Configuration</h1></body></html>");
		}).listen(config().getInteger("http.port", 9080));
	}

	@Override
	public void stop() {
		LOGGER.info("ConfigReadFile Stopped");
	}
}
