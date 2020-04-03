package com.ibm.example.vertx;

/**
 * exmaple of using MongoDB with the event bus. 
 * This is a worker verticle 
 * 
 * @author Brian S Paskin
 * @version 1.0.0 (23/03/2020)
 *  
 */

import java.util.List;

import com.ibm.example.vertx.beans.User;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;

public class MongoVerticle2 extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoVerticle2.class);
	private static MongoClient mongo;

	@Override
	public void start(Future<Void> future) {
		LOGGER.info("MongoVerticle2 Started");

		// create a MongoDB connection and shared pool
		JsonObject mongoConfig = new JsonObject().put("host", config().getString("mongo.host", "localhost"))
				.put("port", config().getInteger("mongo.port", 27017))
				.put("db_name", config().getString("mongo.db", "MongoTest")).put("minPoolSize", 0)
				.put("maxPoolSize", 25).put("connectTimeoutMS", 1000).put("keepAlive", true);
		mongo = MongoClient.createShared(vertx, mongoConfig);

		// Get the event bus
		EventBus bus = vertx.eventBus();

		// get all users, go to mongodb, and then return the results
		bus.consumer("mongo.allUsers", message -> {
			LOGGER.info("MongoVerticle2 getAllUsers");
			mongo.find("users", new JsonObject(), result -> {
				List<JsonObject> results = result.result();
				JsonObject response = new JsonObject().put("users", results);
				message.reply(response);
			});
		});

		// return a single user
		bus.consumer("mongo.getUser", message -> {
			LOGGER.info("MongoVerticle2 getUser");
			String name = (String) message.body();

			// if needing to use options, like find a single user
			FindOptions options = new FindOptions();

			mongo.findWithOptions("users", new JsonObject().put("name", name), options, result -> {
				List<JsonObject> results = result.result();
				JsonObject response = new JsonObject().put("users", results);
				message.reply(response);
			});
		});

		// add a user
		bus.consumer("mongo.addUser", message -> {
			LOGGER.info("MongoVerticle2 addUser");
			String user = (String) message.body();

			// make sure it is a valid user
			Json.decodeValue(user, User.class);

			mongo.save("users", new JsonObject(user), result -> {
				if (result.succeeded()) {
					message.reply("ok");
				} else {
					message.reply("failed : " + result.cause().getMessage());
				}
			});
		});

		// delete a user
		bus.consumer("mongo.deleteUser", message -> {
			LOGGER.info("MongoVerticle2 addUser");
			String name = (String) message.body();

			mongo.removeDocument("users", new JsonObject().put("name", name), result -> {
				if (result.succeeded()) {
					LOGGER.info("user deleted : " + name);
				} else {
					LOGGER.error(result.cause().getMessage());
				}
			});
		});
	}

	@Override
	public void stop(Future<Void> future) {
		LOGGER.info("MongoVerticle2 Stopped");
	}
}
