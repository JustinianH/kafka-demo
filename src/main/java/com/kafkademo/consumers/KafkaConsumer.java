package com.kafkademo.consumers;

import com.kafkademo.models.ChangesInMe;
import com.kafkademo.services.ChangesInMeService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private final ChangesInMeService changesInMeService;

//    For Testing Purposes

    private String payload = null;
    private CountDownLatch latch = new CountDownLatch(1);
    public CountDownLatch getLatch() {
        return latch;
    }

    private void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

//    End Testing

    @KafkaListener(topics = "${topic.name}", groupId = "test")
    public void receiveMessage(ConsumerRecord<?, ?> consumerRecord) {
        LOGGER.info("received payload='{}'", consumerRecord.toString());
        setPayload(consumerRecord.value().toString());
    }

    @KafkaListener(topics = "${topic.second.name}", groupId = "test")
    public void receiveSecondMessage(ConsumerRecord<?, ?> consumerRecord) {
        LOGGER.info("received payload:" + "\n" + "'{}'", consumerRecord.toString());
    }

    @KafkaListener(topics = "changes-in-me", containerFactory = "changesInMeKafkaListenerContainerFactory")
    public void receiveChanges(ChangesInMe changesInMe) {
        LOGGER.info("received payload:" + "\n" + "'{}'", changesInMe);
        changesInMeService.saveChangesInMe(changesInMe);
    }
}
