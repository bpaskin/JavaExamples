package com.ibm.example.kafka.streams;

import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Startup
@Singleton
public class StringStreams {
    
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    
    private KafkaStreams streams;
	
	@Inject
    @ConfigProperty(name = "kafka.bootstrap.servers", defaultValue = "localhost:9092")
	private String bootstrapServers;
	
	@Inject
    @ConfigProperty(name = "kafka.stringTopic", defaultValue = "stringTopic")
	private String stringTopic;
	
	@Inject
    @ConfigProperty(name = "kafka.outStringTopic", defaultValue = "outStringTopic")
	private String outStringTopic;
	
	@Inject
    @ConfigProperty(name = "kafka.string.appId", defaultValue = "streamsGroup")
	private String appId;
	
	@Inject
    @ConfigProperty(name = "kafka.numStreamThreads", defaultValue = "3")
	private String numStreamThreads;
	
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
    	props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    	props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, appId);
    	props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    	props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    	props.setProperty(StreamsConfig.NUM_STREAM_THREADS_CONFIG, numStreamThreads);
    	
        if (kafkaSecure.equalsIgnoreCase("true")) {
        	jaasConfig = String.format(jaasConfig, jaasUserid, jaasPassword);
        	props.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);      
	        props.setProperty("sasl.jaas.config", jaasConfig);
	        props.setProperty("sasl.mechanism", saslMechanism);
	        props.setProperty("ssl.protocol", sslProtocol);
	        props.setProperty("ssl.enabled.protocols", sslEnabledProtocol);
	        props.setProperty("ssl.endpoint.identification.algorithm", sslEnabledIdAlgorithm);
        }

        // Setup topology
    	StreamsBuilder sb = new StreamsBuilder();
    	
    	// Input Topic
    	KStream<String, String> input = sb.stream(stringTopic);    	
    	KStream<String, String> filtered = input.filter( (k,v) -> v.equalsIgnoreCase("hello") );
    	
    	// Output Topic
    	filtered.to(outStringTopic);
    	
    	//Building the topology
    	streams = new KafkaStreams(sb.build(), props);
    	
    	// start processing
    	streams.start();
        
    	logger.exiting(this.getClass().getCanonicalName(), "init");
    }
    
    @PreDestroy
    public void close() {
    	logger.entering(this.getClass().getCanonicalName(), "close");  
    	streams.close();
    	logger.exiting(this.getClass().getCanonicalName(), "close");
    }
}
