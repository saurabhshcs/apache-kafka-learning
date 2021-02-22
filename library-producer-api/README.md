# bootify-kafka

## Apache Kafka Learning

# kafka-for-developers-using-spring-boot

This repository has the complete code related to kafka producers/consumers using spring boot.



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
## cURL command
`
curl -i \
-d '{"libraryEventId":null,"book":{"bookId":456,"bookName":"Kafka Using Spring Boot","bookAuthor":"Saurabh"}}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/v1/libraryevent
`
## Or Endpoint URL for postman or any other RestClient
`
http://localhost:8080/v1/libraryevent
`
## Payload

`
{
	"libraryEventId":"null",
	"book":{
			"bookId":123,
			"bookName":"Apache Kafka with Spring Boot",
			"bookAuthor":"Saurabh"
	
			}
}
`

## Happy Learning!!!
