package com.ibm.example.kafka.streams;

import org.apache.kafka.common.serialization.Serdes.WrapperSerde;

import com.ibm.example.bo.Result;

public final class ResultSerde extends WrapperSerde<Result> {

	public ResultSerde() {
		super(new ResultSerializer(), new ResultDeserializer());
	}
}
