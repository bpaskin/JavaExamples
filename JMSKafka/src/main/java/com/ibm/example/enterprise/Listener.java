package com.ibm.example.enterprise;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import fish.payara.cloud.connectors.kafka.api.KafkaListener;
import fish.payara.cloud.connectors.kafka.api.OnRecord;


@MessageDriven(
		activationConfig = {
		        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "KafkaJCAClient"),
		        @ActivationConfigProperty(propertyName = "groupIdConfig", propertyValue = "myGroup"),
		        @ActivationConfigProperty(propertyName = "topics", propertyValue = "my-pizza"),
		        @ActivationConfigProperty(propertyName = "retryBackoff", propertyValue = "1000"),
		        @ActivationConfigProperty(propertyName = "autoCommitInterval", propertyValue = "100"),
		        @ActivationConfigProperty(propertyName = "keyDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),
		        @ActivationConfigProperty(propertyName = "valueDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),
		        @ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "3000"),
		        @ActivationConfigProperty(propertyName = "commitEachPoll", propertyValue = "true"),
		        @ActivationConfigProperty(propertyName = "useSynchMode", propertyValue = "false")
		})
public class Listener implements KafkaListener {

    @OnRecord()
    public void getMessage(ConsumerRecord<String, String> record) {
        System.out.println("Received record : " + record);
    }
}
