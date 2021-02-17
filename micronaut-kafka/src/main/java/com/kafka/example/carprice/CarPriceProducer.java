package com.kafka.example.carprice;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "id-is-car-price") //which is annotation for kafka producer, id is not madetory
public interface CarPriceProducer {

    @Topic("car-price") //topic name
    void send(@KafkaKey String symbol, CarPrice carPrice);
    //producing kafkakey is optional
}
