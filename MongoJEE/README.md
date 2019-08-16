# MongoJEE #

Simple example of how to use MongoDB with JEE.  This uses JAX-RS, CDI, EJBLite, and security.  The example is using SSL to connect to the remote MongoDB, so it is **important** to import the public key of MongoDB into the truststore of the application server.  Change the *ConnectionProvider* class with the **proper IP/hostname and port** to connect.
