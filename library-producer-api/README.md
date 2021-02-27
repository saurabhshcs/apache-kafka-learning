# bootify-kafka

## PART-1 Library event producer api
> This application is creating a topic programmatically. Please see [AutoCreateTopicConfig.java](https://github.com/saurabhshcs/apache-kafka-learning/blob/main/library-producer-api/src/main/java/com/techsharezone/library/producer/api/config/AutoCreateTopicConfig.java)
> This repository has the complete code related to kafka producers/consumers using spring boot.
> I have used 3 Apache Kafka clusters and 3 replicas in this example. 
> Please see following server.properties

```
-rw-r--r--   1 saurabhshcs  admin  6864 19 Feb 23:40 server-1.properties
-rw-r--r--   1 saurabhshcs  admin  6864 19 Feb 23:41 server-2.properties
-rw-r--r--   1 saurabhshcs  admin  6863 17 Feb 22:57 server.properties
```

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

## aplication.yaml for auto configurations

```
spring:
  profiles:
    active: local
    
spring:
  profiles: local
  kafka:
    template:
      default-topic: library-events
    producer:
      bootstrap-servers: localhost:9092, localhost:9093, localhost:9094
      key-serializer: org.apache.kafka.common.serialization.IntegerSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    admin:
      properties:
        bootstarp.servers: localhost:9092, localhost:9093, localhost:9094
```

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
  "libraryEventId":"null",
  "book":{
	 "bookId":123,
	 "bookName":"Apache Kafka with Spring Boot",
	 "bookAuthor":"Saurabh"

	}
}

```
## Console Logs

```
2021-02-27 00:41:12.804  INFO 35836 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2021-02-27 00:41:12.804  INFO 35836 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2021-02-27 00:41:12.806  INFO 35836 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
2021-02-27 00:41:12.954  INFO 35836 --- [nio-8080-exec-1] o.a.k.clients.producer.ProducerConfig    : ProducerConfig values: 
        acks = 1
        batch.size = 16384
        bootstrap.servers = [localhost:9092, localhost:9093, localhost:9094]
        buffer.memory = 33554432
        client.dns.lookup = use_all_dns_ips
        client.id = producer-1
        compression.type = none
        connections.max.idle.ms = 540000
        delivery.timeout.ms = 120000
        enable.idempotence = false
        interceptor.classes = []
        internal.auto.downgrade.txn.commit = true
        key.serializer = class org.apache.kafka.common.serialization.IntegerSerializer
        linger.ms = 0
        max.block.ms = 60000
        max.in.flight.requests.per.connection = 5
        max.request.size = 1048576
        metadata.max.age.ms = 300000
        metadata.max.idle.ms = 300000
        metric.reporters = []
        metrics.num.samples = 2
        metrics.recording.level = INFO
        metrics.sample.window.ms = 30000
        partitioner.class = class org.apache.kafka.clients.producer.internals.DefaultPartitioner
        receive.buffer.bytes = 32768
        reconnect.backoff.max.ms = 1000
        reconnect.backoff.ms = 50
        request.timeout.ms = 30000
        retries = 2147483647
        retry.backoff.ms = 100
        sasl.client.callback.handler.class = null
        sasl.jaas.config = null
        sasl.kerberos.kinit.cmd = /usr/bin/kinit
        sasl.kerberos.min.time.before.relogin = 60000
        sasl.kerberos.service.name = null
        sasl.kerberos.ticket.renew.jitter = 0.05
        sasl.kerberos.ticket.renew.window.factor = 0.8
        sasl.login.callback.handler.class = null
        sasl.login.class = null
        sasl.login.refresh.buffer.seconds = 300
        sasl.login.refresh.min.period.seconds = 60
        sasl.login.refresh.window.factor = 0.8
        sasl.login.refresh.window.jitter = 0.05
        sasl.mechanism = GSSAPI
        security.protocol = PLAINTEXT
        security.providers = null
        send.buffer.bytes = 131072
        ssl.cipher.suites = null
        ssl.enabled.protocols = [TLSv1.2, TLSv1.3]
        ssl.endpoint.identification.algorithm = https
        ssl.engine.factory.class = null
        ssl.key.password = null
        ssl.keymanager.algorithm = SunX509
        ssl.keystore.location = null
        ssl.keystore.password = null
        ssl.keystore.type = JKS
        ssl.protocol = TLSv1.3
        ssl.provider = null
        ssl.secure.random.implementation = null
        ssl.trustmanager.algorithm = PKIX
        ssl.truststore.location = null
        ssl.truststore.password = null
        ssl.truststore.type = JKS
        transaction.timeout.ms = 60000
        transactional.id = null
        value.serializer = class org.apache.kafka.common.serialization.StringSerializer

2021-02-27 00:41:12.981  INFO 35836 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.6.0
2021-02-27 00:41:12.982  INFO 35836 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 62abe01bee039651
2021-02-27 00:41:12.982  INFO 35836 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1614386472981
2021-02-27 00:41:12.993  INFO 35836 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: QRxGzd66R6iVmc5qoW5Dqg
2021-02-27 00:41:13.114  INFO 35836 --- [ad | producer-1] c.t.l.p.a.producer.LibraryEventProducer  : The message sent successfully to the key[null] and the value is [{"libraryEventId":null,"book":{"numberOfPages":0}}]

```

> Topic created 

## Run cmd for viewing the topics in kafka broker

`
./kafka-topics --zookeeper localhost:2181 --list
`

```
__consumer_offsets
first-replicated-topic
library-event
```

## Happy Learning!!!


[Linkedin](https://www.linkedin.com/in/saurabhshcs/) | Like & Subscribe my channel - [YouTube](https://www.youtube.com/channel/UCSQqjPw7_tfx1Ie4yYHbcxQ?pbjreload=102) | Follow me @ [StackOverFlow](https://stackoverflow.com/users/10719720/saurabhshcs?tab=profile)
