package com.zen.lab.dojo.kafka;

public interface MessageProducer {
    void send(String topic, String key, String message);
}
