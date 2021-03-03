package com.techsharezone.libraryeventconsumerapi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@Configuration
@EnableKafka
@Profile("local")
public class LibraryEventConsumerConfig {
}
