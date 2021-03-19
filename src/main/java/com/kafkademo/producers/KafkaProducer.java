package com.kafkademo.producers;

import com.kafkademo.models.ChangesInMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

//    Define templates with accurate intake data types

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, ChangesInMe> changesInMeTemplate;

    public void send(String topic, String payload) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }

    public void sendMessageWithKey(String topic, String key, String payload) {
        LOGGER.info("sending payload='{}' with key='{} 'to topic='{}'", payload, key, topic);
        kafkaTemplate.send(topic, key, payload);
    }

    public void sendChangesInMe(String topic, ChangesInMe changesInMe) {
        LOGGER.info("sending payload='{}' to topic='{}'", changesInMe, topic);
        changesInMeTemplate.send(topic, changesInMe);
    }
}
