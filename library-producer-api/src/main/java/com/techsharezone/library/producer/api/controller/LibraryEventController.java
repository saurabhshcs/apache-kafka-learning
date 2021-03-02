package com.techsharezone.library.producer.api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsharezone.library.producer.api.domain.LibraryEvent;
import com.techsharezone.library.producer.api.domain.LibraryEventType;
import com.techsharezone.library.producer.api.producer.LibraryEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@RestController
@Slf4j
public class LibraryEventController {

    @Autowired
    private LibraryEventProducer producer;

    @PostMapping("/v1/asyncLibraryEvent")
    public ResponseEntity<LibraryEvent> asyncLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {

        log.info("Before Async libraryEvent..");

        producer.sendLibraryEvent(libraryEvent);

        log.info("After send libraryEvent..");

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("/v1/syncLibraryEvent")
    public ResponseEntity<LibraryEvent> syncLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {

        log.info("Before Sync libraryEvent..");

        SendResult<Integer, String> sendResult = producer.sendLibraryEventSynchronous(libraryEvent);
        log.info("SendResult values: {}", sendResult.toString());
        log.info("After send libraryEvent..");

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("/v1/syncLibraryEventWithTimeoutFeature")
    public ResponseEntity<LibraryEvent> syncLibraryEventWithTimeoutFeature(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, TimeoutException {

        log.info("Before Sync libraryEventWithTimeoutFeature..");

        SendResult<Integer, String> sendResult = producer.sendLibraryEventSynchronousWithTimeoutFeature(libraryEvent);
        log.info("SendResult values: {}", sendResult.toString());
        log.info("After send libraryEvent..");

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("/v1/asyncLibraryEventWithSend")
    public ResponseEntity<LibraryEvent> asyncLibraryEventWithSend(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, TimeoutException {

        log.info("Before Async libraryEventWithSync..");

        producer.sendLibraryEventAsyncWithSend(libraryEvent);
        log.info("After send libraryEvent..");

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("/v1/asyncLibraryEventWithHeader")
    public ResponseEntity<LibraryEvent> asyncLibraryEventWithHeader(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, TimeoutException {

        log.info("Before Async libraryEventWithSync..");

        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        producer.sendLibraryEventAsyncWithHeader(libraryEvent);
        log.info("After send libraryEvent..");

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
