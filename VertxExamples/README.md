# Vert.x Examples #

This is a collection of standalone Vert.x examples that go from the very basics to the more advanced

### HelloWorld ###
A simple sample that does not use a verticle and responds on all requests.

[http://localhost:9080/](http://localhost:9080/)

### HelloWorld2 ###
The same as above, but uses a single verticle.  

[http://localhost:9080/](http://localhost:9080/)

### HelloWorld3 ###
Same as above, but uses a Handler.

[http://localhost:9080/](http://localhost:9080/)

### ServeWebPages ###
This is a sample showing that web pages can be served.

[http://localhost:9080/](http://localhost:9080/)

[http://localhost:9080/index.html](http://localhost:9080/index.html)

[http://localhost:9080/hello.html](http://localhost:9080/hello.html)

### ServeWebPages2 ###
The same as above, but a name parameter can be passed to the hello page.

[http://localhost:9080/](http://localhost:9080/)

[http://localhost:9080/index.html](http://localhost:9080/index.html)

[http://localhost:9080/hello.html?name=Dante](http://localhost:9080/hello.html?name=Dante)

### ConfigHardcoded ###
Sample of using deployment options to pass configuration information to the verticle

[http://localhost:9080/](http://localhost:9080/)

### ConfigReadFile ###
This is the same as above, but reads the configuration YAML file from the ``conf/config.json`` file in the resources directory.

[http://localhost:9080/](http://localhost:9080/)

### ServeJson ###
This uses the Router to assign requests.  This has an global failure handler, preprocessor, and keeps users stored in memory.  Users can be added, removed, listed, or list all users using the correct HTTP Method.

List all users:
GET [http://localhost:9080/v1/users](http://localhost:9080/v1/users)

List a single user:
GET [http://localhost:9080/v1/user/:name](http://localhost:9080/v1/user/:name)

Delete a single user:
DELETE [http://localhost:9080/v1/user/delete/:name](http://localhost:9080/v1/user/delete/:name)

Add a single user:
POST [http://localhost:9080/v1/user/add](http://localhost:9080/v1/user/add)

``{"name":"Mario","occupations":[{"occupation":"Plumber" },{"occupation":"Handyman"}]}``

### JsonWithMongo ###
This is the same as above, but sends requests to Mongo DB using the Vert.x Mongo Client.  The MongoDB host, port, and DB name are hardcoded and will need to be changed.

List all users:
GET [http://localhost:9080/v1/users](http://localhost:9080/v1/users)

List a single user:
GET [http://localhost:9080/v1/user/:name](http://localhost:9080/v1/user/:name)

Delete a single user:
DELETE [http://localhost:9080/v1/user/delete/:name](http://localhost:9080/v1/user/delete/:name)

Add a single user:
POST [http://localhost:9080/v1/user/add](http://localhost:9080/v1/user/add)

``{"name":"Mario","occupations":[{"occupation":"Plumber" },{"occupation":"Handyman"}]}``

### MongoApp ###
This uses 2 verticles, MongoVerticle1 and MongoVerticle2, and the Event Bus to communicate.  Everything is configurable from the configuration file.  The MongoVerticle1 is the JSON request/reply engine.  The MongoVerticle2 is a worker verticle and handles all the MongoDB interaction.  All Handlers are done with lambdas.  

List all users:
GET [http://localhost:9080/v1/users](http://localhost:9080/v1/users)

List a single user:
GET [http://localhost:9080/v1/user/:name](http://localhost:9080/v1/user/:name)

Delete a single user:
DELETE [http://localhost:9080/v1/user/:name](http://localhost:9080/v1/user/:name)

Add a single user:
POST [http://localhost:9080/v1/user](http://localhost:9080/v1/user)

``{"name":"Mario","occupations":[{"occupation":"Plumber" },{"occupation":"Handyman"}]}``


