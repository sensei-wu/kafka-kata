package com.zen.lab.dojo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("asyncproducer")
public class AsyncMessageProducer implements MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncMessageProducer.class);

    private final KafkaProducer<String, String> kafkaProducer;

    @Autowired
    public AsyncMessageProducer(KafkaProducer<String, String> kafkaProducer) {this.kafkaProducer = kafkaProducer;}

    @Override
    public void send(String topic, String key, String message) {
        kafkaProducer.send(new ProducerRecord<>(topic, key, message), (metadata, exception) -> {
            if(exception != null) {
                exception.printStackTrace();
            } else {
                LOGGER.info("--Async-- Partition={}, offser={}", metadata.partition(), metadata.offset());
            }
        });
    }
}
