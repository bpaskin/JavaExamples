# Using Liberty with Rabbit MQ with a Resource Adapter #

In this example I am using a Docker image of Rabbit MQ and setting it up for the needs of the sample application.  The sample application takes either a REST POST with a message to send a message to a Queue or a REST GET to receive a message from the Queue.

### Setup Rabbit MQ ###
1. Create a docker image : 
`docker build -t "rabbitmq:test" .`

2. Run a container with the web and AMQP ports open : 
`docker run -d -p 15672:15672 -p 5672:5672 -t "rabbitmq:test"`

3. In a browser navigate to http://host:15672/#/queues of where the Docker image is running.  The userid is "guest" and the password is "guest"

4. Add a "classic" Queue called "test" that is "transient"

### Download the Resource Adapter (RA) ###
The Resource Adapter will allow standard Java Messaging System (JMS) commands to be used to communicate between Liberty and Rabbit MQ.  The RA can be download from the [Maven Repository](https://mvnrepository.com/artifact/org.amqphub.jca/resource-adapter/1.0.0). The RA should be placed in a location that Liberty can access.  In this example it is looking for the RA in the shared resources directory (wlp/usr/shared/resources).

### Running the example ###
Once the application is compiled and placed in the `dropins` folder of Liberty, start the application server (`wlp/bin/server start serverName`).

to place a message on the Queue:
`curl -X POST http://host:port/RabbitMQ/v1/send/ThisIsMyMessage`

to retrieve a message from the Queue:
`curl -X GET http://host:port/RabbitMQ/v1/receive`