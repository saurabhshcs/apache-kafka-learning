package com.techsharezone.library.producer.api.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@Configuration
@Profile("local")
public class AutoCreateTopicConfig {

    @Bean
    public NewTopic libraryEvent() {
        return TopicBuilder.name("library-event")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
