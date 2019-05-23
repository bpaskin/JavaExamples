package com.ibm.example.kafka.streams;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.common.serialization.Serializer;

import com.ibm.example.bo.Result;

public class ResultSerializer implements Serializer<Result> {
	
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {}

	@Override
	public byte[] serialize(String topic, Result data) {
		logger.entering(this.getClass().getCanonicalName(), "serialize");

		try {	
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(data);
			out.flush();
		
			logger.entering(this.getClass().getCanonicalName(), "serialize");
			return bos.toByteArray();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace(System.err);
		}
		
		logger.entering(this.getClass().getCanonicalName(), "serialize - NULL");
		return null;
	}
	
	@Override
	public void close() {}

}
