package com.ibm.example.kafka.consumer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.common.serialization.Deserializer;

import com.ibm.example.bo.Result;

public class ResultDeserializer implements Deserializer<Result> {
	
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {}

	@Override
	public Result deserialize(String topic, byte[] data) {
		logger.entering(this.getClass().getCanonicalName(), "deserialize");
		
    	try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInput in = new ObjectInputStream(bis);
			Object o = in.readObject(); 
			
			if (o instanceof Result) {
				logger.exiting(this.getClass().getCanonicalName(), "deserialize");
				return (Result) o;
			}
			
			throw new Exception ("value is not an instance of Result");
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace(System.err);
		}
		logger.exiting(this.getClass().getCanonicalName(), "deserialize - NULL");
		return null;
	}

	@Override
	public void close() {}

}
