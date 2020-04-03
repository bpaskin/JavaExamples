package com.ibm.example.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * First example of using Vertx Simple setup and return of a String for all
 * requests without using a verticle
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 * 
 *          http://localhost:9080 or any uri http://localhost:9080/hello
 *
 */

public class HelloWorld {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorld.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.createHttpServer().requestHandler(routingContext -> {
			routingContext.response().end("<html><body><h1>Hello World!</h1></body></html>");
		}).listen(9080);

		LOGGER.info("HelloWorld Started");
	}
}
