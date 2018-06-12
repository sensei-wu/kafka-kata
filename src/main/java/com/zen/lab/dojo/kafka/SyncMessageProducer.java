package com.zen.lab.dojo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;

@Service("syncproducer")
public class SyncMessageProducer implements MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    private final KafkaProducer<String, String> kafkaProducer;

    @Autowired
    public SyncMessageProducer(KafkaProducer<String, String> kafkaProducer) {this.kafkaProducer = kafkaProducer;}

    public void send(String topic, String key, String message) {

        try {
            final RecordMetadata recordMetadata = kafkaProducer.send(new ProducerRecord<>(topic, key, message)).get();
            LOGGER.info("--Sync-- Partition={}, offset={}", recordMetadata.partition(), recordMetadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void cleanClose() {
        kafkaProducer.close();
    }
}
