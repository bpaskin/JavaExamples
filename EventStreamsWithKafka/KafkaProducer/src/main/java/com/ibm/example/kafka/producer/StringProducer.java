package com.ibm.example.kafka.producer;

import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class StringProducer {
	
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
	private KafkaProducer<String, String> producer;
	
	@Inject
    @ConfigProperty(name = "kafka.bootstrap.servers", defaultValue = "localhost:9092")
	private String bootstrapServers;
	
	@Inject
    @ConfigProperty(name = "kafka.idempotence", defaultValue = "false")
	private String idempotence;
	
	@Inject
    @ConfigProperty(name = "kafka.compression.type", defaultValue = "none")
	private String compressionType;
	
	@Inject
    @ConfigProperty(name = "kafka.linger", defaultValue = "0")
	private String lingerMs;
	
	@Inject
    @ConfigProperty(name = "kafka.batchSize", defaultValue = "16384")
	private String batchSize;
	
	@Inject
    @ConfigProperty(name = "kafka.acks", defaultValue = "0")
	private String acks;
	
	@Inject
    @ConfigProperty(name = "kafka.stringTopic", defaultValue = "stringTopic")
	private String stringTopic;
	
	@Inject
    @ConfigProperty(name = "kafka.secure", defaultValue = "false")
	private String kafkaSecure;
	
	@Inject
    @ConfigProperty(name = "security.protocol", defaultValue = "SASL_SSL")
	private String securityProtocol;
	
	@Inject
    @ConfigProperty(name = "sasl.mechanism", defaultValue = "PLAIN")
	private String saslMechanism;
	
	@Inject
    @ConfigProperty(name = "sasl.jaas.config", defaultValue = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";")
	private String jaasConfig;
	
	@Inject
    @ConfigProperty(name = "sasl.jaas.userid", defaultValue = "userid")
	private String jaasUserid;
	
	@Inject
    @ConfigProperty(name = "sasl.jaas.password", defaultValue = "password")
	private String jaasPassword;
    
	@Inject
	@ConfigProperty(name = "ssl.protocol", defaultValue = "TLSv1.2")
	private String sslProtocol;
	
	@Inject
	@ConfigProperty(name = "ssl.enabled.protocols", defaultValue = "TLSv1.2")
	private String sslEnabledProtocol;
	
	@Inject
	@ConfigProperty(name = "ssl.endpoint.identification.algorithm", defaultValue = "HTTPS")
	private String sslEnabledIdAlgorithm;
	
    @PostConstruct
    private void init() {
    	logger.entering(this.getClass().getCanonicalName(), "init");
    	
    	Properties props = new Properties();
    	props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, idempotence);
        props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
        props.setProperty(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.setProperty(ProducerConfig.ACKS_CONFIG, acks);
        
        if (kafkaSecure.equalsIgnoreCase("true")) {
        	jaasConfig = String.format(jaasConfig, jaasUserid, jaasPassword);
            props.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
            props.setProperty("sasl.jaas.config", jaasConfig);
            props.setProperty("sasl.mechanism", saslMechanism);
            props.setProperty("ssl.protocol", sslProtocol);
            props.setProperty("ssl.enabled.protocols", sslEnabledProtocol);
            props.setProperty("ssl.endpoint.identification.algorithm", sslEnabledIdAlgorithm);
        }
        
        producer = new KafkaProducer<>(props);
        
    	logger.exiting(this.getClass().getCanonicalName(), "init");
    }
	
    @PreDestroy
    public void close() {
    	logger.entering(this.getClass().getCanonicalName(), "close");
    	
    	producer.close();
    	
    	logger.exiting(this.getClass().getCanonicalName(), "close");
    }
	
    public void publish(String key, String... messages) {
    	logger.entering(this.getClass().getCanonicalName(), "publish");
    	
    	for (final String message : messages) {
    		ProducerRecord<String, String> record;
    		
    		if (key == null) {
    			record = new ProducerRecord<>(stringTopic, message);
    		} else {
    			record = new ProducerRecord<>(stringTopic, key, message);	
    		}	
    		
    		logger.info("send messages: " + message);
    		producer.send(record);
    		logger.info("sent messages" );
    	}
    	
    	logger.exiting(this.getClass().getCanonicalName(), "publish");
    }
}
