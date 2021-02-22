package com.techsharezone.library.producer.api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsharezone.library.producer.api.domain.LibraryEvent;
import com.techsharezone.library.producer.api.producer.LibraryEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@RestController
public class LibraryEventController {

    @Autowired
    private LibraryEventProducer producer;

    @PostMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
        producer.sendLibraryEvent(libraryEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
