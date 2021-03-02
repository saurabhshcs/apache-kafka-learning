package com.techsharezone.library.producer.api.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsharezone.library.producer.api.domain.LibraryEvent;
import com.techsharezone.library.producer.api.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@Component
@Slf4j
public class LibraryEventProducer {

    private final static String TOPIC_NAME = "library-vent-003";

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, (MessageException) ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    public SendResult<Integer, String> sendLibraryEventSynchronous(final LibraryEvent libraryEvent) throws JsonProcessingException {
        final Integer key = libraryEvent.getLibraryEventId();
        final String value = objectMapper.writeValueAsString(libraryEvent);
        SendResult<Integer, String> sendResult = null;

        try {
            sendResult = kafkaTemplate.sendDefault(key, value).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Exception here, while sending the message: {}", e.getMessage());
        }
        return sendResult;
    }

    public SendResult<Integer, String> sendLibraryEventSynchronousWithTimeoutFeature(LibraryEvent libraryEvent) throws JsonProcessingException, TimeoutException {
        final Integer key = libraryEvent.getLibraryEventId();
        final String value = objectMapper.writeValueAsString(libraryEvent);
        SendResult<Integer, String> sendResult = null;

        try {
            sendResult = kafkaTemplate.sendDefault(key, value).get(2, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Exception here, while sending the message: {}", e.getMessage());
        }
        return sendResult;
    }

    public void sendLibraryEventAsyncWithSend(LibraryEvent libraryEvent) throws JsonProcessingException, TimeoutException {
        final Integer key = libraryEvent.getLibraryEventId();
        final String value = objectMapper.writeValueAsString(libraryEvent);

        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, TOPIC_NAME);

        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(TOPIC_NAME, key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, (MessageException) ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    public void sendLibraryEventAsyncWithHeader(LibraryEvent libraryEvent) throws JsonProcessingException {
        final Integer key = libraryEvent.getLibraryEventId();
        final String value = objectMapper.writeValueAsString(libraryEvent);

        ProducerRecord<Integer, String> producerRecord = buildProducerRecordWithHeader(key, value, TOPIC_NAME);

        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(TOPIC_NAME, key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, (MessageException) ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    private ProducerRecord<Integer, String> buildProducerRecordWithHeader(Integer key, String value, String topicName) {
        List<Header> headers = List.of(new RecordHeader("event-source", "mannual".getBytes()));

        return new ProducerRecord<>(topicName, null, key, value, headers);
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topicName) {
        return new ProducerRecord<>(topicName, null, key, value, null);
    }

    private void handleFailure(Integer key, String value, MessageException ex) {
        log.error("Error while sending the message to the key [{}] for value [{}]", key, value);

        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Errot on Failure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("The message sent successfully to the key[{}] and the value is [{}]", key, value);
    }
}
