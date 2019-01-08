package com.cloudlearn.kafka.demo;

import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.util.Map;

public class Pere implements Serializer<Persss> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Persss persss) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
