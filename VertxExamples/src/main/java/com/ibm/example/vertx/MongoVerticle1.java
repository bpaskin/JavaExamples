package com.ibm.example.vertx;

/**
 * Example of serving sending and receiving JSON with
 * different HTTP Methods using an event bus
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 *  
 */

import com.ibm.example.vertx.beans.User;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MongoVerticle1 extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoVerticle1.class);

	@Override
	public void start(Future<Void> future) {
		LOGGER.info("MongoVerticle1 Started");

		// Create the router
		Router router = Router.router(vertx);

		// get the event bus to send and receive messages
		// Event bus has 2 send options.
		// publish is to fire and forget
		// request is to send and wait for reply
		EventBus bus = vertx.eventBus();

		// Accept and return only JSON
		router.route().consumes("application/json");
		router.route().produces("application/json");

		// Default error handler
		router.route().failureHandler(context -> {
			LOGGER.error(context.failure().getMessage());
			context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
					.end(context.failure().getMessage());
		});

		// Match routes in order

		// do some preprocessing for all requests. Doing nothing.
		router.route("/v1/*").handler(context -> {
			LOGGER.info("MongoVerticle1 preprocesssor");
			context.next();
		});

		// HTTP GET all users
		router.get("/v1/users").handler(context -> {
			LOGGER.info("MongoVerticle1 getAllUsers");

			// send a request over the bus and then wait for reply
			bus.request("mongo.allUsers", null, reply -> {
				if (reply.succeeded()) {
					JsonObject results = (JsonObject) reply.result().body();
					context.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encodePrettily(results));
				} else {
					LOGGER.error("getAllUsers failed : " + reply.cause().getMessage());
					context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
							.end(reply.cause().getMessage());
				}
			});
		});

		// HTTP GET user with specific name
		router.get("/v1/user/:name").handler(context -> {
			LOGGER.info("MongoVerticle1 getUser");
			String name = context.request().getParam("name");

			bus.request("mongo.getUser", name, reply -> {
				if (reply.succeeded()) {
					JsonObject results = (JsonObject) reply.result().body();
					context.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encodePrettily(results));
				} else {
					LOGGER.error("getUser failed : " + reply.cause().getMessage());
					context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
							.end(reply.cause().getMessage());
				}
			});
		});

		// Process the body to allow anything that matches /v1/user/add can
		// be processed by routingContext
		// can use /v1/user* for all
		// This is needed to allow to getBody from the request
		router.route("/v1/user*").handler(BodyHandler.create());

		// HTTP POST to add user
		// ie. curl -H "Content-Type: application/json" -X POST -d
		// '{"name":"Mario","occupations":[{"occupation":"Plumber" }]}'
		// http://localhost:9080/v1/user/add
		router.post("/v1/user").handler(context -> {
			LOGGER.info("MongoVerticle1 addUser");

			// make sure it is fine
			Json.decodeValue(context.getBodyAsString(), User.class);

			bus.request("mongo.addUser", context.getBodyAsString(), reply -> {
				if (reply.succeeded() && reply.result().body().toString().equalsIgnoreCase("ok")) {
					context.response().setStatusCode(HttpResponseStatus.OK.code()).end("user added");
				} else {
					LOGGER.error("addUser failed : " + reply.cause().getMessage());
					context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
							.end(reply.cause().getMessage());
				}
			});
		});

		// HTTP DELETE remove user
		// ie. curl -X DELETE http://localhost:9080/v1/user/delete/Mario
		router.delete("/v1/user/:name").handler(context -> {
			LOGGER.info("MongoVerticle1 deleteUser");
			String name = context.request().getParam("name");
			bus.publish("mongo.deleteUser", name);
			context.response().setStatusCode(HttpResponseStatus.OK.code()).end("user deleted");
		});

		// get the http.port from the config file that was passed in
		// if it is not found, then use port 9080 as the default
		int port = config().getInteger("http.port", 9080);

		// create the http server using the port and router
		vertx.createHttpServer().requestHandler(router).listen(port);
	}

	@Override
	public void stop(Future<Void> future) {
		LOGGER.info("MongoVerticle1 Stopped");
	}
}
