package com.techsharezone.libraryeventconsumerapi.consumer;


import com.techsharezone.libraryeventconsumerapi.notification.NotificationHeader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@Component
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class LibraryEventConsumer {

    @KafkaListener(topics = {"library-events-04"})
    public void onMessage(ConsumerRecord<Integer, String> message) {

        System.out.println("Message Header: " + message.headers());

        String messageType = getMessageType(message.headers());
        System.out.println("Kafka message has been received [" + messageType +"] with body -  " + message.value());
    }

    private String getMessageType(Headers headers) {
        if (headers != null) {
            Header lastHeader = headers.lastHeader(NotificationHeader.MESSAGE_TYPE.getHeaderValue());
            return lastHeader != null && lastHeader.value() != null ? new String(lastHeader.value(), StandardCharsets.UTF_8): null;
        }
        return null;
    }
}
