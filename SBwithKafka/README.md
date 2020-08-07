*** Using Spring/SpringBoot with Kafka in Liberty and WebSphere ***

[Spring](https://spring.io) current does not offer a global pluggable [SSLContext](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/net/ssl/SSLContext.htm), a socket implementation, which leads to ignoring the settings of a container, like [OpenLiberty](https://openliberty.io).  Each component must be overridden directly.  This is no different when using the Spring [KafkaTemplate](https://docs.spring.io/spring-kafka/reference/html/).

Kafka has introduced a pluggable [SslEngineFactory](https://github.com/apache/kafka/blob/trunk/clients/src/main/java/org/apache/kafka/common/security/ssl/DefaultSslEngineFactory.java) in version 2.60 that can be used.

This is only needed when the Kafka Brokers are secured.

To use OpenLiberty/WebSphere's keystore and truststore the [JSSEHelper](https://www.ibm.com/support/knowledgecenter/SSD28V_liberty/com.ibm.websphere.javadoc.liberty.doc/com.ibm.websphere.appserver.api.ssl_1.3-javadoc/com/ibm/websphere/ssl/JSSEHelper.html) is used to set the necessary Kafka properties:

```
Properties props = JSSEHelper.getInstance().getProperties("mySSLSettings");

securityProps.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, props.get("com.ibm.ssl.keyStore"));
securityProps.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, props.get("com.ibm.ssl.keyStoreType"));
securityProps.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, props.get("com.ibm.ssl.keyStorePassword"));
			
securityProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, props.get("com.ibm.ssl.trustStore"));
securityProps.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, props.get("com.ibm.ssl.trustStoreType"));
securityProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, props.get("com.ibm.ssl.trustStorePassword"));

```

Where the `getProperties(String)` of the `JSSEHelper` is the `id` of the `ssl` stanza in Liberty

```
<ssl id="mySSLSettings" clientAuthenticationSupported="true"  keyStoreRef="defaultKeyStore" securityLevel="HIGH" sslProtocol="TLSv1.2" trustStoreRef="defaultTrustStore"/>
```

In Traditional WebSphere the `getProperties(String)` of the `JSSEHelper` is the name of the SSL Configuration being used.  

