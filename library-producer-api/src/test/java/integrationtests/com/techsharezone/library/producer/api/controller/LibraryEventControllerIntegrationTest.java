package com.techsharezone.library.producer.api.controller;

import com.techsharezone.library.producer.api.domain.Book;
import com.techsharezone.library.producer.api.domain.LibraryEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "kafka"})
@EmbeddedKafka(topics = {"library-events"}, partitions = 3)
class LibraryEventControllerIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    void postLibraryEvent() {
        //given
        Book book = Book.builder()
                .id(123)
                .name("Apache Kafka")
                .author("Saurabh")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE.toString());
        HttpEntity <LibraryEvent> request = new HttpEntity<>(libraryEvent, headers);

        //when
        ResponseEntity<? extends LibraryEvent> responseEntity = testRestTemplate.exchange("/v1/asyncLibraryEvent", HttpMethod.POST, request, libraryEvent.getClass());

        //then
        assertThat(HttpStatus.CREATED, is(responseEntity.getStatusCode()));
    }

    @Test
    void postLibraryEventWithnullEventId() {
        //given
        Book book = Book.builder()
                .id(null)
                .name("Apache Kafka")
                .author("Saurabh")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE.toString());
        HttpEntity <LibraryEvent> request = new HttpEntity<>(libraryEvent, headers);

        //when
        ResponseEntity<? extends LibraryEvent> responseEntity = testRestTemplate.exchange("/v1/asyncLibraryEvent", HttpMethod.POST, request, libraryEvent.getClass());

        //then
        assertThat(HttpStatus.CREATED, is(responseEntity.getStatusCode()));
    }
}