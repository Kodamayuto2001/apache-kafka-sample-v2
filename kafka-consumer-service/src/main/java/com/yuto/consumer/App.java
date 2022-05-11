package com.yuto.consumer;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


public class App {

	public static void main(String args[]) throws UnknownHostException {
	    Properties config = new Properties();
	    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	    config.put(ConsumerConfig.GROUP_ID_CONFIG, "java-consumer-group");
	    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

	    KafkaConsumer<String, String> consumer =
	        new KafkaConsumer<>(config, new StringDeserializer(), new StringDeserializer());

	    consumer.subscribe(Arrays.asList("mytopic"));

	    try {
	    	while (true) {
	    		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(60l));

	    		for (ConsumerRecord<String, String> record : records) {
	    			System.out.println(String.format("%s:%s", record.offset(), record.value()));
	    		}

	    		consumer.commitSync();
	    	}
	    } finally {
	    	consumer.close();
	    }
	}
}
