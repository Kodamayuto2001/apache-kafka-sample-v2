package com.yuto.producer;

import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class App {

	public static void main(String args[]) throws UnknownHostException {
		Properties properties = new Properties();
    	properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    	KafkaProducer<String, String> producer = new KafkaProducer<>(properties, new StringSerializer(), new StringSerializer());


    	for (int i = 0; i < 10; i++) {
    		producer.send(new ProducerRecord<String, String>("mytopic", String.format("message%02d", i)));
    	}

    	producer.close();

	}
}
