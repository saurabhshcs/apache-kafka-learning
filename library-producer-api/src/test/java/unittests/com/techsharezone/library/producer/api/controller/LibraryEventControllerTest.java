package com.techsharezone.library.producer.api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsharezone.library.producer.api.domain.Book;
import com.techsharezone.library.producer.api.domain.LibraryEvent;
import com.techsharezone.library.producer.api.producer.LibraryEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@WebMvcTest(LibraryEventController.class)
@AutoConfigureMockMvc
public class LibraryEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryEventProducer producer;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void postLibraryEvent() throws Exception {

        Book book = Book.builder()
                .id(123)
                .name("Apache Kafka")
                .author("Saurabh")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        String json = objectMapper.writeValueAsString(libraryEvent);

        doNothing().when(producer).sendLibraryEvent(libraryEvent);

        mockMvc.perform(post("/v1/asyncLibraryEvent")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated() );
    }
}
