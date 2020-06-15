### JMS20 Example ###

This is a sample project that uses Web Services (JAX-WS) and RESTful services (JAX-RS) to send a message and receive a message based on the JMS Correlation ID using the simplified JMS 2.0 APIs and injecting the JMS Context.  A Message Driven Bean (MDB) is used to pick up the message in the input Queue and placed in the output Queue.

Using curl:

JAX-WS:

`curl -X POST -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://services.example.ibm.com/"><soapenv:Header/><soapenv:Body><ser:sendMessage><arg0>Hello There!</arg0></ser:sendMessage></soapenv:Body></soapenv:Envelope>' http://host:port/JMS20/SendReceiveService`

JAX-RS:

`curl -X POST -d 'Hello There!' --header "Content-Type: application/json" http://host:port/JMS20Example/v1/send`

