package com.ibm.example.spring.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.ibm.websphere.ssl.JSSEHelper;

@Configuration
public class Configurations {

	@Value("${kafka.bootstrap.servers}")
	private String bootstrap_servers_config;

	@Value("${kafka.security.protocol:SASL_SSL}")
	private String protocol;

	@Value("${kafka.sasl.mechanism:PLAIN}")
	private String mechanism;

	@Value("${kafka.username}")
	private String username;

	@Value("${kafka.password}")
	private String password;

	@Value(value = "${kafka.acks:all}")
	private String acks;
	
	@Value(value = "${kafka.buffer:32768}")
	private String buffer;
	
	@Value(value = "${kafka.retries:3}")
	private String retries;
	
	@Value(value = "${kafka.batch.size:16384}")
	private String batchSize;
	
	@Value(value = "${kafka.transaction.timeout:30000}")
	private String txTimeout;

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}
	
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> producerProps = new HashMap<String, Object>();
		producerProps.put(ProducerConfig.ACKS_CONFIG, this.acks);
		producerProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, this.buffer);
		producerProps.put(ProducerConfig.RETRIES_CONFIG, this.retries);
		producerProps.put(ProducerConfig.BATCH_SIZE_CONFIG, this.batchSize);
		producerProps.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, this.txTimeout);
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

		producerProps.putAll(commonConfig());
		return new DefaultKafkaProducerFactory<String, String>(producerProps);
	}
	
	private Map<String, Object> commonConfig()  {
		Map<String, Object> securityProps = new HashMap<String, Object>();
		securityProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrap_servers_config);
		securityProps.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, this.protocol);
		securityProps.put(SaslConfigs.SASL_MECHANISM, this.mechanism);
		securityProps.put(SaslConfigs.SASL_JAAS_CONFIG,
				"org.apache.kafka.common.security.plain.PlainLoginModule required\nusername=\"" + this.username
						+ "\"\npassword=\"" + this.password + "\";");
		
		// used to override SSLContext used by Kafka from Liberty or traditional WebSphere
		// for Liberty use the ssl id
		// for tWAS use the SSL configuration name in use
		// <ssl id="mySSLSettings" keyStoreRef="defaultKeyStore" trustStoreRef="defaultTrustStore" securityLevel="HIGH" sslProtocol="TLSv1.2"/> 
		//
		// To override the SslEngineFactory instead
		// (https://github.com/apache/kafka/blob/trunk/clients/src/main/java/org/apache/kafka/common/security/ssl/DefaultSslEngineFactory.java)
		// then use:
		//
		// securityProps.put(SslConfigs.SSL_ENGINE_FACTORY_CLASS_CONFIG, YourSslEngineFactory.class);
		try {
			Properties props = JSSEHelper.getInstance().getProperties("mySSLSettings");

			securityProps.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, props.get("com.ibm.ssl.keyStore"));
			securityProps.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, props.get("com.ibm.ssl.keyStoreType"));
			securityProps.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, props.get("com.ibm.ssl.keyStorePassword"));
			
			securityProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, props.get("com.ibm.ssl.trustStore"));
			securityProps.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, props.get("com.ibm.ssl.trustStoreType"));
			securityProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, props.get("com.ibm.ssl.trustStorePassword"));

		} catch (Exception e) {
			throw new KafkaException(e);
		}
		return securityProps;
	}
}
