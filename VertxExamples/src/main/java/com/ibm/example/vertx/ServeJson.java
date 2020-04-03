package com.ibm.example.vertx;

/**
 * Example of serving sending and receiving JSON with
 * different HTTP Methods
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 *  
 */

import java.util.ArrayList;
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
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class ServeJson extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServeJson.class);
	private static List<User> users = new ArrayList<>();

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(new ServeJson());
	}

	// Synchronous start . Can you an Asynchronous start too.
	@Override
	public void start() {
		LOGGER.info("ServeJson Started");
		loadUsers();

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
	}

	@Override
	public void stop() {
		LOGGER.info("ServeJson Stopped");
	}

	private void preprocesssor(RoutingContext context) {
		LOGGER.info("ServeJson preprocesssor");
		// Can do some work here, if necessary

		// go to the next processing element
		context.next();
	}

	private void getAllUsers(RoutingContext context) {
		LOGGER.info("ServeJson getAllUsers");
		JsonObject response = new JsonObject();
		response.put("users", users);
		context.response().setStatusCode(HttpResponseStatus.OK.code())
				.putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(Json.encodePrettily(response));
	}

	private void getUser(RoutingContext context) {
		LOGGER.info("ServeJson getUser");

		String name = context.request().getParam("name");

		if (null == name) {
			context.response().setStatusCode(400).end("User name not sent");
			return;
		}

		JsonObject response = new JsonObject();

		for (User user : users) {
			if (user.getName().equalsIgnoreCase(name)) {
				response = new JsonObject(Json.encode(user));
				break;
			}
		}

		context.response().setStatusCode(HttpResponseStatus.OK.code()).putHeader("Content-Type", "application/json")
				.end(Json.encodePrettily(response));
	}

	private void addUser(RoutingContext context) {
		LOGGER.info("ServeJson addUser");
		try {

			User user = Json.decodeValue(context.getBodyAsString(), User.class);

			users.add(user);
			context.response().setStatusCode(HttpResponseStatus.CREATED.code())
					// .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
					.end("user added");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			context.response().setStatusCode(500).end("Error adding user");
		}
	}

	private void deleteUser(RoutingContext context) {
		LOGGER.info("ServeJson deleteUser");

		String name = context.request().getParam("name");

		if (null == name) {
			context.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end("User name not sent");
			return;
		}

		for (User user : users) {
			if (user.getName().equalsIgnoreCase(name)) {
				users.remove(user);
				break;
			}
		}

		context.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encodePrettily("User removed"));
	}

	private void error(RoutingContext context) {
		LOGGER.info("ServeJson error");

		Throwable failure = context.failure();
		context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(failure.getMessage());
	}

	private void loadUsers() {
		LOGGER.info("ServeJson loadUsers");
		User user = new User();
		user.setName("Luke");
		user.setOccupationUtil("Jedi Knight");
		users.add(user);

		user = new User();
		user.setName("Leia");
		user.setOccupationUtil("Princess", "Rebel Leader", "General");
		users.add(user);

		user = new User();
		user.setName("Han");
		user.setOccupationUtil("Bounty Hunter", "Rebel");
		users.add(user);
	}
}
