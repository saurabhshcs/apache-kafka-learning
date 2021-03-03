package com.techsharezone.libraryeventconsumerapi.consumer;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@Component
@Slf4j
public class LibraryEventConsumer {

    @KafkaListener(topics = {"library-events"})
    public void onMessage(ConsumerRecord<Integer, String> record) {
//        log.info("Consumer Record--- {}", record);
    }
}
