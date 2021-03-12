package com.kafkademo.kafka;

import com.kafkademo.consumers.KafkaConsumer;
import com.kafkademo.producers.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@DirtiesContext
@NoArgsConstructor
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9090", "port=9090" })
public class EmbeddedKafkaIntegrationTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConsumer kafkaConsumer;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingtoSimpleProducer_thenMessageReceived()
            throws Exception {
        kafkaProducer.send("test-topic", "Sending with own simple KafkaProducer");
        kafkaConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertEquals(1L, kafkaConsumer.getLatch().getCount());
        assertEquals("Sending with own simple KafkaProducer", kafkaConsumer.getPayload());
    }
}
