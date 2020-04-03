package com.ibm.example.vertx;

/**
 * Example of serving sending and receiving JSON with
 * different HTTP Methods and using MongoDB to store,
 * retrieve, and delete users
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 *  
 */

import java.util.List;

import com.ibm.example.vertx.beans.User;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class JsonWithMongo extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonWithMongo.class);

	private static MongoClient mongo;

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new JsonWithMongo());
	}

	// Synchronous start . Can you an Asynchronous start too.
	@Override
	public void start() {
		LOGGER.info("JsonWithMongo Started");

		Router router = Router.router(vertx);

		// Default error handler
		router.route().failureHandler(this::error);

		// Accept and return only JSON
		router.route().consumes("application/json");
		router.route().produces("application/json");

		// Match routes in order

		// do some preprocessing for all requests
		router.route("/v1/*").handler(this::preprocesssor);

		// HTTP GET all users
		router.get("/v1/users").handler(this::getAllUsers);

		// HTTP GET user with specific name
		router.get("/v1/user/:name").handler(this::getUser);

		// Process the body to allow anything that matches /v1/user/add can
		// be processed by routingContext
		// can use /v1/user* for all
		// This is needed to allow to getBody from the request
		router.route("/v1/user/add").handler(BodyHandler.create());

		// HTTP POST to add user
		// ie. curl -H "Content-Type: application/json" -X POST -d
		// '{"name":"Mario","occupations":[{"occupation":"Plumber" }]}'
		// http://localhost:9080/v1/user/add
		router.post("/v1/user/add").handler(this::addUser);

		// HTTP DELETE remove user
		// ie. curl -X DELETE http://localhost:9080/v1/user/delete/Mario
		router.delete("/v1/user/delete/:name").handler(this::deleteUser);

		vertx.createHttpServer().requestHandler(router).listen(9080);

		// create a MongoDB connection and shared pool
		JsonObject mongoConfig = new JsonObject().put("host", "lovecraft").put("port", 27017)
				.put("db_name", "MongoTest")
				.put("minPoolSize", 0)
				.put("maxPoolSize", 25)
				.put("connectTimeoutMS", 1000)
				.put("keepAlive", true);
		mongo = MongoClient.createShared(vertx, mongoConfig);
		loadUsers();
	}

	@Override
	public void stop() {
		LOGGER.info("JsonWithMongo Stopped");
	}

	private void preprocesssor(RoutingContext context) {
		LOGGER.info("JsonWithMongo preprocesssor");
		// Can do some work here, if necessary

		// go to the next processing element
		context.next();
	}

	private void getAllUsers(RoutingContext context) {
		LOGGER.info("JsonWithMongo getAllUsers");

		mongo.find("users", new JsonObject(), result -> {
			try {
				List<JsonObject> results = result.result();
				JsonObject response = new JsonObject().put("users", results);
				context.response().setStatusCode(HttpResponseStatus.OK.code())
						.putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(Json.encodePrettily(response));
			} catch (Exception e) {
				LOGGER.error(e);
				context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
						.putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(e.getMessage());
			}
		});
	}

	private void getUser(RoutingContext context) {
		LOGGER.info("JsonWithMongo getUser");

		String name = context.request().getParam("name");

		if (null == name) {
			context.response().setStatusCode(HttpResponseStatus.NOT_ACCEPTABLE.code()).end("User name not sent");
			return;
		}

		// Can you findWithOptions, if necessary
		FindOptions options = new FindOptions();
		// options.setLimit(1);

		mongo.findWithOptions("users", new JsonObject().put("name", name), options, result -> {
			try {
				List<JsonObject> results = result.result();
				JsonObject response = new JsonObject().put("users", results);
				context.response().setStatusCode(HttpResponseStatus.OK.code())
						.putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(Json.encodePrettily(response));
			} catch (Exception e) {
				LOGGER.error(e);
				context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
						.putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(e.getMessage());
			}
		});
	}

	private void addUser(RoutingContext context) {
		LOGGER.info("JsonWithMongo addUser");

		// make sure it is fine
		Json.decodeValue(context.getBodyAsString(), User.class);

		mongo.save("users", context.getBodyAsJson(), result -> {
			if (result.succeeded()) {
				context.response().setStatusCode(HttpResponseStatus.CREATED.code())
						.putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end("user added");
			} else {
				context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
						.end(result.cause().getMessage());
				result.cause().printStackTrace();
			}
		});
	}

	private void deleteUser(RoutingContext context) {
		LOGGER.info("JsonWithMongo deleteUser");

		String name = context.request().getParam("name");

		if (null == name) {
			context.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end("User name not sent");
			return;
		}

		mongo.removeDocument("users", new JsonObject().put("name", name), result -> {
			if (result.succeeded()) {
				context.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encodePrettily("User removed"));
			} else {
				context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
						.end(result.cause().getMessage());
				result.cause().printStackTrace();
			}
		});
	}

	private void error(RoutingContext context) {
		LOGGER.info("JsonWithMongo error");

		Throwable failure = context.failure();
		context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(failure.getMessage());
	}

	private void loadUsers() {
		LOGGER.info("JsonWithMongo loadUsers");

		User user = new User();
		user.setName("Luke");
		user.setOccupationUtil("Jedi Knight");

		mongo.insert("users", new JsonObject(Json.encode(user)), result -> {
			if (!result.succeeded()) {
				result.cause().printStackTrace();
			}
		});

		user = new User();
		user.setName("Leia");
		user.setOccupationUtil("Princess", "Rebel Leader", "General");

		mongo.insert("users", new JsonObject(Json.encode(user)), result -> {
			if (!result.succeeded()) {
				result.cause().printStackTrace();
			}
		});

		user = new User();
		user.setName("Han");
		user.setOccupationUtil("Bounty Hunter", "Rebel");

		mongo.insert("users", new JsonObject(Json.encode(user)), result -> {
			if (!result.succeeded()) {
				result.cause().printStackTrace();
			}
		});

	}
}
