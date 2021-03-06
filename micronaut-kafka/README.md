Micronaut-kafka application
----------------------------
A simple micronaut app for beginners, which creates and interacts with kafka producers and consumer.

features
---------
1. Micronaut
2. Kafka
3. Kafka Testcontainers
4. Docker - for kafka image and testcontainers

Logic: 
A scheduler posts messages to Topic1 using producer1 ever 10s,
A consumer consumes messages in batch from topic 1, and forwards it to Producer2,
Producer2 posts message to topic 2 in batch.

System requirements:
---------------------
Docker

How to run the application
--------------------------
1. cd kafka
2. docker-compose up // this will start the kaka container (lenses.io) on localhost:3030
3. gradlew clean build
4. run the jar or applikcation.main()

## Micronaut 2.3.2 Documentation

- [User Guide](https://docs.micronaut.io/2.3.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.3.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.3.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature kafka documentation

- [Micronaut Kafka Messaging documentation](https://micronaut-projects.github.io/micronaut-kafka/latest/guide/index.html)
