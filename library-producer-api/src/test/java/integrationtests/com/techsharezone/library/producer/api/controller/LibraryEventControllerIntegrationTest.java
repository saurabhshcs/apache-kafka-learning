package com.techsharezone.library.producer.api.controller;

import com.techsharezone.library.producer.api.domain.Book;
import com.techsharezone.library.producer.api.domain.LibraryEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Autowired
    Libra


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
    void postLibraryEventWithNullEventId() {
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

    @Test
    @Timeout(5)
    void putLibraryEvent() throws InterruptedException {
        //given
        Book book = Book.builder()
                .id(456)
                .name("Apache Kafka in Action")
                .author("Saurabh")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(321)
                .book(book)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<LibraryEvent> request = new HttpEntity<>(libraryEvent, headers);

        //when
        ResponseEntity<LibraryEvent> responseEntity = testRestTemplate.exchange("/v1/libraryevent", HttpMethod.PUT, request, LibraryEvent.class);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ConsumerRecord<Integer, String> consumerRecord = KafkaTestUtils.getSingleRecord(consumer, "library-events");
        //Thread.sleep(3000);
        String expectedRecord = "{\"libraryEventId\":123,\"libraryEventType\":\"UPDATE\",\"book\":{\"bookId\":456,\"bookName\":\"Kafka using Spring Boot\",\"bookAuthor\":\"Dilip\"}}";
        String value = consumerRecord.value();
        assertEquals(expectedRecord, value);

    }

}