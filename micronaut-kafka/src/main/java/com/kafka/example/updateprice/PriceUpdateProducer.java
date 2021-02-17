package com.kafka.example.updateprice;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.reactivex.Flowable;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.List;

@KafkaClient(
        id = "id-is-price-update",
        batch = true
)
public interface PriceUpdateProducer {

    @Topic("price_update")
    Flowable<RecordMetadata> send(List<PriceUpdate> prices);
}
