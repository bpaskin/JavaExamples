package com.ibm.example.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * First example of using Vertx Simple setup and return of a String for all
 * requests
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 * 
 *          http://localhost:9080 or any uri http://localhost:9080/hello
 *
 */

public class HelloWorld2 extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorld2.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new HelloWorld2());
	}

	@Override
	public void start() {
		LOGGER.info("HelloWorld2 Started");

		// start listening on port 9080 for all requests and respond with Hello World!
		vertx.createHttpServer().requestHandler(routingContext -> {
			routingContext.response().end("<html><body><h1>Hello World 2!</h1></body></html>");
		}).listen(9080);
	}

	@Override
	public void stop() {
		LOGGER.info("HelloWorld2 Started");
	}
}
