[Kafka](https://kafka.apache.org) is a Stream Processor.  [Event Streams](https://ibm.github.io/event-streams/) is IBM's implementation of Kafka, which includes some enterprise features and other features.  This can also be used with Event Streams on [IBM Cloud](https://cloud.ibm.com/catalog/services/event-streams).

There are three samples included, **KafkaProducer**, **KafkaConsumer**, and **KafkaStreams**. Each contains the necessary Maven `pom.xml` file, code, and Liberty `server.xml` and `bootstrap.properties` files.  The samples make use of [OpenLiberty](https://openliberty.io) or [WebSphere Liberty](https://developer.ibm.com/wasdev/websphere-liberty/) features, including [MicroProfile Configuration](https://microprofile.io), JAX-RS, CDI, and EJB Lite.  Each sample uses MP Config to inject the necessary properties into the application, which are taken from the `bootstrap.properties` file.  There are two Serdes, a String and a Result Object. 

To enable security for Event Streams update the `bootstrap.properties` files by setting `kafka.secure=true`, and setting `sasl.jaas.userid` and `sasl.jaas.password` with the information retrieved from the credentials given in the **Service credentials** panel after selecting the created Event Streams service.  The `kafka.bootstrap.servers` should be updated with the proper information. 

Topics _**MUST**_ be created before running the samples.

### KafkaProducer ####
The Kafka Producer has two ways to call it, one with a GET and one with a POST.  The GET will only send the message 'Hello' to the Topic Specified.  The POST will take a last part of the URI and do a search on Google for the first 10 results and store the URL in a Result Object and send the Result Object to another Topic.

Doing a GET to `http://host:port/KafkaProducer/jaxrs/search/hello` sends the String 'Hello' to a Topic.  The return is a String to the caller is 'Hello World @ _current time since epoch_'.

Doing a POST to `http://host:port/KafkaProducer/jaxrs/search/{term}` send the Result Object to a Topic.  The Return is a List of Result Objects.

**Example:**
`curl -X POST http://host:post/KafkaProducer/jaxrs/search/black+metal`

returns:
```
[
   {
      "searchString":"black+metal",
      "timesSeached":"16:14:32.862",
      "url":"https://en.wikipedia.org/wiki/Black_metal"
   },
   {
      "searchString":"black+metal",
      "timesSeached":"16:14:32.862",
      "url":"https://en.wikipedia.org/wiki/Early_Norwegian_black_metal_scene"
   },
   ...
]
```

### KafkaConsumer ####

Not too exciting, but prints out the returned Objects (String or Result Object) to `System.out`.  

### KafkaStreams ####

For the String Topic it takes the input Topic, matches it against the word 'Hello', and sends it to the output Topic.

For the Result Topic it takes the input Topic, checks to see if the URL begins with 'https', and sends only those results to the output Topic.

The Consumer should be setup to read the output Topic.
