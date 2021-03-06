## PART-2 Library event consumer api with multiple instances on Mac.

<details><summary>What is this example for ?</summary>

#### Apache Kafka Consumer API for sending message to a kafka topic by following ways:
- Reading message from the kafka topic `library-events` using the object of `ConsumerRecord<Integer, String>`.<br/> 
  Please refer the consumer class - [LibraryEventConsumer.java](https://github.com/saurabhshcs/apache-kafka-learning/blob/main/library-event-consumer-api/src/main/java/com/techsharezone/libraryeventconsumerapi/consumer/LibraryEventConsumer.java)
</details>

<details><summary>Application Overview & Required configurations </summary>
	
- This application is developed for consuming the apache kafka messages based on the the certain or specific events.
- This repository has the complete code related to kafka producers/consumers using spring boot.
- I have used 3 Apache Kafka clusters and 3 replicas in this example. 
- Please see following server.properties

#### Config files in `{KAFKA_HOME}/config/`
```
-rw-r--r--   1 saurabhshcs  admin  6864 19 Feb 23:40 server-1.properties
-rw-r--r--   1 saurabhshcs  admin  6864 19 Feb 23:41 server-2.properties
-rw-r--r--   1 saurabhshcs  admin  6863 17 Feb 22:57 server.properties
```
#### Application configurations in aplication.yaml for auto configurations

```
spring:
  profiles:
    active: local
server:
  port: 8081

---
spring:
  profiles: local
  kafka:
    consumer:
      bootstrap-servers: localhost:9092,localhost:9093,localhost:9094
      key-deserializer: org.apache.kafka.common.serialization.IntegerDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      group-id: library-events-listener-group

```
</details>

## View Setup Apache Kafka Developer guide

- [Setup-Kafka](https://github.com/saurabhshcs/apache-kafka-developer-guide/blob/main/README.md)

## Securing your Kafka Cluster using SSL

- [Kafka SSL SetUp](https://github.com/saurabhshcs/apache-kafka-developer-guide/blob/main/Kafka_Security_config.md)

## MySQL Database

## Run all the test 

`
./gradlew test
`

## Run application
`
./gradlew bootRun
`
#### Run multiple instances of this consumer application
```
java -jar build/libs/library-event-consumer-api-0.0.1-SNAPSHOT.jar
java -jar Dserver.port:8082 build/libs/library-event-consumer-api-0.0.1-SNAPSHOT.jar
java -jar Dserver.port:8083 build/libs/library-event-consumer-api-0.0.1-SNAPSHOT.jar
```
<details><summary>Automation Tests</summary>
<details> <summary>Integration Tests </summary>

#### LibraryEventControllerIntegrationTest

```
./gradlew clean test

> Task :test
2021-03-02 00:56:40.670  INFO 52992 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
2021-03-02 00:56:40.671  INFO 52992 --- [extShutdownHook] o.a.k.clients.producer.KafkaProducer     : [Producer clientId=producer-1] Closing the Kafka producer with timeoutMillis = 30000 ms.

BUILD SUCCESSFUL in 16s
5 actionable tasks: 5 executed
SAURABHs-MacBook-Pro:library-producer-api saurabhsharma$ 

```
</details>
<details><summary>Unit Tests</summary>

#### LibraryEventControllerTest

```
./gradlew clean test

> Task :test
2021-03-02 00:56:40.670  INFO 52992 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
2021-03-02 00:56:40.671  INFO 52992 --- [extShutdownHook] o.a.k.clients.producer.KafkaProducer     : [Producer clientId=producer-1] Closing the Kafka producer with timeoutMillis = 30000 ms.

BUILD SUCCESSFUL in 16s
5 actionable tasks: 5 executed
SAURABHs-MacBook-Pro:library-producer-api saurabhsharma$ 

```
</details>
</details>

## cURL command

```
curl -i \
-d '{"libraryEventId":null,"book":{"bookId":456,"bookName":"Kafka Using Spring Boot","bookAuthor":"Saurabh"}}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/v1/libraryevent
```

## Or Endpoint URL for postman or any other RestClient

```
http://localhost:8080/v1/libraryevent
````
## Payload

```
{
   "libraryEventId":null,
   "book":{
      "id":456,
      "name":"Kafka Using Spring Boot",
      "author":"Saurabh Sharma"
   },
   "libraryEventType":null
} 
```

<details><summary>View logs</summary>
<p>

#### Console Logs -01
```
2021-03-03 23:22:58.003  INFO 72860 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : library-events-listener-group: partitions assigned: [library-events-04-0]
Consumer Record--- {}ConsumerRecord(topic = library-events-04, partition = 0, leaderEpoch = 0, offset = 0, CreateTime = 1614813844613, serialized key size = -1, serialized value size = 93, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = {"libraryEventId":null,"book":{"id":null,"name":null,"author":null},"libraryEventType":"NEW"})
Consumer Record--- {}ConsumerRecord(topic = library-events-04, partition = 0, leaderEpoch = 0, offset = 1, CreateTime = 1614813845663, serialized key size = -1, serialized value size = 93, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = {"libraryEventId":null,"book":{"id":null,"name":null,"author":null},"libraryEventType":"NEW"})

```
## View the topic in kafka broker using following cmd

`./kafka-topics --zookeeper localhost:2181 --list`

#### List of the topics
```
__consumer_offsets
library-events
```

#### View the messages for the topic `library-event`
`
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic library-events  --from-beginning
`

## Happy Learning!!!


[Linkedin](https://www.linkedin.com/in/saurabhshcs/) | Like & Subscribe my channel - [YouTube](https://www.youtube.com/channel/UCSQqjPw7_tfx1Ie4yYHbcxQ?pbjreload=102) | Follow me @ [StackOverFlow](https://stackoverflow.com/users/10719720/saurabhshcs?tab=profile)
