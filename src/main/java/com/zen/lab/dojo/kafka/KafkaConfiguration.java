package com.zen.lab.dojo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaProducer<String, String> kafkaProducer() {
        return new KafkaProducer<>(createProperties(),
                new StringSerializer(),
                new StringSerializer());
    }

    private Properties createProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        /* setting the below make sure that only once message is written to all in sync replicas, it is considered committed
          default value of "acks" is 1, which means a message is considered committed once it is successfully written to leader
          setting this value to 0 might result in message losses as in that case, messages are considered committed before any of the
          server acknowledges the commit
        */
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }
}
