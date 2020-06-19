### ActiveMQ Example with Open Liberty ###

[ActiveMQ](https://activemq.apache.org) Classic is an open source JMS 1.1 compliant message queueing server.  Since it is JMS 1.1 compliant, it can easily integrated with OpenLiberty/Liberty.

Requirements:
1. An ActiveMQ Server must be started and can be monitored with the dashboard, which can be found at http://host:8161/admin
2. The [ActiveMQ Resource Adapter](https://activemq.apache.org/resource-adapter) must be downloaded and placed in a directory that can be accessed by OpenLiberty/Liberty and configured in the `server.xml`.

The `server.xml` will need to be updated to match the settings of the ActiveMQ RA, URL and Queue name.

To place a message on the Queue:
`curl -d "My Test Message" -X POST http://host:port/ActiveMQ/v1/send`

To read a message from the Queue:
`curl http://host:port/ActiveMQ/v1/get`