package org.prd.bookservice.service;

import lombok.extern.slf4j.Slf4j;
import org.prd.bookservice.model.dto.BookEventDto;
import org.prd.bookservice.web.exception.EventNotArriveException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventException;

@Slf4j
@Service
public class BookEventService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookEventService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String topic, BookEventDto event) {
        kafkaTemplate.send(topic, event).thenAccept(result -> {
            log.info("Sent message=[{}] with offset=[{}]", event, result.getRecordMetadata().offset());
        }).exceptionally(e -> {
            log.error("Unable to send message=[{}] due to : {}", event, e.getMessage());
            throw new EventNotArriveException("Exception not expect, try later");
        });
    }
}