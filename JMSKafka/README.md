*** JMS Kafka Example ***

This example is using the [resource adapter](https://github.com/payara/Cloud-Connectors/tree/master/Kafka) provided by Payara and the setup in Open Liberty / Liberty.  

The resource adapter must be downloaded and placed in a directory that is accessible by Liberty.  The current resource adpater can be compiled or downloaded from the maven repository.  It is important that the JCA and Kafka JMS Client libraries are not placed in the application, or this may cause a classloader issue.

The kafka host and port must be added to the Liberty server.xml:

```
<variable name="kafkaHost" value="host:port"/>
```

The example application sends a message with to the topic `my-pizza` with a random choice of 5 pizza types.

```
curl http://localhost:9080/JMSKafka/v1/send
Message sent at Thu Jun 04 18:48:22 CEST 2020
```

The application is using a Message Driven Bean to pickup the messages and will be printed out in the messages.log file:

```
Received record : ConsumerRecord(topic = my-pizza, partition = 0, leaderEpoch = 0, offset = 29, CreateTime = 1591289302063, serialized key size = -1, serialized value size = 11, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = Capricciosa)
```