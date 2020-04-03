package com.ibm.example.vertx;

import java.io.File;
import java.util.Scanner;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Serve web pages (placed in the resource/webroot directory) using a Router.
 * Replaces name of {name} with parameter sent. If no name is sent, then Mario
 * replaces the name
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 * 
 *          http://localhost:9080 or http://localhost:9080/hello.html
 *          http://localhost:9080/hello.html?name=brian
 *
 */

public class ServeWebPages2 extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServeWebPages2.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new ServeWebPages2());
	}

	@Override
	public void start() {
		LOGGER.info("ServeWebPages2 Started");

		Router router = Router.router(vertx);

		// catch the hello.html and do something with it
		router.route("/hello.html").handler(routingContext -> {

			String name = routingContext.request().getParam("name");

			if (null == name) {
				name = "Mario";
			}

			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("webroot/hello.html").getFile());

			StringBuilder webpage = new StringBuilder("");
			try {
				Scanner scanner = new Scanner(file);

				while (scanner.hasNext()) {
					webpage.append(scanner.nextLine().replace("{name}", name));
				}

				scanner.close();

				routingContext.response().setStatusCode(200).end(webpage.toString());

			} catch (Exception e) {
				routingContext.response().setStatusCode(400).end("An error has occurred: " + e.getMessage());
			}
		});

		// if no routes match, use the default router
		router.route().handler(StaticHandler.create().setCachingEnabled(false));

		vertx.createHttpServer().requestHandler(router).listen(9080);
	}

	@Override
	public void stop() {
		LOGGER.info("ServeWebPages2 Started");
	}
}
