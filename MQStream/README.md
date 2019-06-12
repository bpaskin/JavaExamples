#### MQ / JMS can do stream processing too! ####

This is an example using [MicroProfile Reactive Streams](https://microprofile.io/project/eclipse/microprofile-reactive-messaging) along with [JMS](https://javaee.github.io/jms-spec/), using [IBM MQ](https://www.ibm.com/products/mq), to put messages on a Queue or Topic, use stream processing to check the contents, and place them on an output Queue to be consumed by a consumer.  The example was tested on [IBM WebSphere Liberty](https://www.ibm.com/cloud/websphere-liberty), but can be run on [OpenLiberty](https://openliberty.io), or any Application Server that supports JEE8.  Any JMS can be used, as long as the proper resource adapter is installed and configured.  For OpenLiberty the wmqJmsClient would need to be replaced by adding the IBM MQ as an external resource adapter.

The application utilises JAX-RS (RESTful Services), JMS (Java Messaging Service), MDBs (Message Driven Beans), and MicroProfile Reactive Streams.  A message is sent via a RESTFul post to the service with a search term `curl -X POST http://host:port/MQStream/jaxrs/search/Norwegian+Black+Metal`.  The term is then searched on Google and the URLs are extracted and placed in an Object that is then sent to an `IN.Q` on the JMS system.  An MDB listens for messages and a Stream is used to find Objects that contain 'https'.  Those Objects are then sent to an `OUT.Q` and the others are dropped. An MDB then reads the `OUT.Q` and prints out the URL.

It is a simple example, but shows how Streams can be used with JMS.

Liberty configuration is included, but will need to be updated for the location of the MQ / JMS system and the MQ resource Asdapter.  The necessary Queues are defined in the MQStream.mqsc file, though the necessary permissions will need to be added.
