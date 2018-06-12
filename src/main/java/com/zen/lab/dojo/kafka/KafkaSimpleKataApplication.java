package com.zen.lab.dojo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KafkaSimpleKataApplication implements CommandLineRunner{

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSimpleKataApplication.class);

    private MessageProducer messageProducer;

    @Autowired
    public KafkaSimpleKataApplication(@Qualifier("asyncproducer") MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaSimpleKataApplication.class);
    }

    public void run(String... args) throws Exception {
        while(true) {
            messageProducer.send("kafka-kata", "jaba", "Hello Jaba");
            TimeUnit.MILLISECONDS.sleep(2000);
        }
    }
}
