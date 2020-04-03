package com.ibm.example.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Serve web pages (placed in the resource/webroot directory) using a Router
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 * 
 *          http://localhost:9080 or any uri http://localhost:9080/hello.html
 *
 */

public class ServeWebPages extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServeWebPages.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new ServeWebPages());
	}

	@Override
	public void start() {
		LOGGER.info("ServeWebPages Started");

		Router router = Router.router(vertx);
		router.route().handler(StaticHandler.create().setCachingEnabled(false));

		vertx.createHttpServer().requestHandler(router).listen(9080);

	}

	@Override
	public void stop() {
		LOGGER.info("ServeWebPages Started");
	}
}
