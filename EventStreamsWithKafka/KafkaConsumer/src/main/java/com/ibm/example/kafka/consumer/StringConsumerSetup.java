package com.ibm.example.kafka.consumer;

import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Startup
@Singleton
public class StringConsumerSetup {

    @Resource
    ManagedExecutorService mes;
    
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
	@Inject
    @ConfigProperty(name = "kafka.bootstrap.servers", defaultValue = "localhost:9092")
	private String bootstrapServers;
	
	@Inject
    @ConfigProperty(name = "kafka.stringTopic", defaultValue = "stringTopic")
	private String stringTopic;
	
	@Inject
    @ConfigProperty(name = "kafka.stringGroupId", defaultValue = "consumerStringGroupId")
	private String stringGroup;
	
	@Inject
    @ConfigProperty(name = "kafka.consumerInstances", defaultValue = "3")
	private String instances;
	
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
    	props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, stringGroup);
        
        if (kafkaSecure.equalsIgnoreCase("true")) {
        	jaasConfig = String.format(jaasConfig, jaasUserid, jaasPassword);
        	props.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);      
	        props.setProperty("sasl.jaas.config", jaasConfig);
	        props.setProperty("sasl.mechanism", saslMechanism);
	        props.setProperty("ssl.protocol", sslProtocol);
	        props.setProperty("ssl.enabled.protocols", sslEnabledProtocol);
	        props.setProperty("ssl.endpoint.identification.algorithm", sslEnabledIdAlgorithm);
        }
        
        for (int i = 0; i < Integer.parseInt(instances); i++) {
        	logger.fine("Creating new String consumer thread");
        	mes.execute(new StringConsumer(props, stringTopic));
        }
        
    	logger.exiting(this.getClass().getCanonicalName(), "init");
    }
    
    @PreDestroy
    public void close() {
    	logger.entering(this.getClass().getCanonicalName(), "close");    	
    	logger.exiting(this.getClass().getCanonicalName(), "close");
    }
}
