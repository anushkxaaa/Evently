package com.evently.consumer;

import com.evently.common.cdc.CdcMessageProcessor;
import com.evently.common.dto.CdcEventPayload;
import com.evently.common.dto.DebeziumMessage;
import com.evently.dto.EventCdcRow;
import com.evently.service.EventChangeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final EventChangeHandler eventChangeHandler;

    @KafkaListener(topics = "${kafka.cdc.topic-pattern}", groupId = "evt-mongo-read")
    public void consume(ConsumerRecord<String, String> record) {
        log.info("Received Kafka message: {}", record.value());
        try {
            CdcEventPayload<EventCdcRow> payload = parse(record.value());
            CdcMessageProcessor.process(payload, eventChangeHandler);
        } catch (Exception e) {
            log.error("Failed to process Kafka message for key={}", record.key(), e);
        }
    }

    private CdcEventPayload<EventCdcRow> parse(String rawJson) throws Exception {
        DebeziumMessage<EventCdcRow> message = objectMapper.readValue(
                rawJson,
                objectMapper.getTypeFactory()
                        .constructParametricType(
                                DebeziumMessage.class,
                                EventCdcRow.class
                        )
        );
        return message.getPayload();
    }
}