package com.cloudlearn.kafka;

import com.alibaba.fastjson.JSON;
import com.cloudlearn.kafka.demo.Persss;
import com.fasterxml.jackson.databind.util.JSONPObject;
import kafka.utils.json.JsonObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaApplicationTests {
	private KafkaProducer<String,String> producer;
	private Properties properties;

	@Test
	public void contextLoads() throws ExecutionException, InterruptedException {
		properties=new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("batch.size", 16384);
		properties.put("linger.ms", 1);
		properties.put("buffer.memory", 33554432);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer=new KafkaProducer<String, String>(properties);
        Persss persss=new Persss();
        persss.setName("aaa");
        persss.setName1("bbb");
        String jsonObject= com.alibaba.fastjson.JSONObject.toJSONString(persss);
		ProducerRecord<String,String> producerRecord=new ProducerRecord<>("test1124",jsonObject);

        RecordMetadata recordMetadata = producer.send(producerRecord).get();
        System.out.println(recordMetadata.topic());
        System.out.println(recordMetadata.partition());

        producer.close();
	}

}
