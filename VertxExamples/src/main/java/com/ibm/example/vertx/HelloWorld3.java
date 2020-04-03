package com.ibm.example.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Another Hello World, but using a Handler without lambdas
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 * 
 *          http://localhost:9080 or any uri http://localhost:9080/hello
 *
 */

public class HelloWorld3 extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorld3.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new HelloWorld3());
	}

	@Override
	public void start() {
		LOGGER.info("HelloWorld3 Started");

		vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {

			@Override
			public void handle(HttpServerRequest request) {
				LOGGER.info("Incoming request");
				request.response().end("<html><body><h1>Hello World 3!</h1></body></html>");
			}

		}).listen(9080);
	}

	@Override
	public void stop() {
		LOGGER.info("HelloWorld3 Started");
	}
}
