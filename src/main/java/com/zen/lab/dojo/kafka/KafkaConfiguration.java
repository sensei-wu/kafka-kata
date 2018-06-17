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
        /* Setting the below parameter makes sure that only once messages are written to all of the in sync replicas,
           they are considered committed. <br/>
           The default value of "acks" is 1, which means that a message is considered committed once it is successfully written to
           leader broker. <br/>
           Setting this value to 0 might result in message losses as in that case, messages are considered committed before any of the
           server acknowledges the commit (fire and forget)
        */
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }
}
