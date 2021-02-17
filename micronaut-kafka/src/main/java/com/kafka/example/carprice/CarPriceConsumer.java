package com.kafka.example.carprice;

import com.kafka.example.updateprice.PriceUpdate;
import com.kafka.example.updateprice.PriceUpdateProducer;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@KafkaListener(
        clientId = "id-is-car-price",
        groupId = "car-price-consumer",
        batch = true,
        offsetReset = OffsetReset.EARLIEST
)
public class CarPriceConsumer {

    private final PriceUpdateProducer priceUpdateProducer;

    public CarPriceConsumer(final PriceUpdateProducer priceUpdateProducer) {
        this.priceUpdateProducer = priceUpdateProducer;
    }

    @Topic("car-price")
    void receive(List<CarPrice> carPrices) {
        log.debug("Consuming batch of car prices: "+ carPrices);
        // getting car price and sending it to price update related topic
        final List<PriceUpdate> priceUpdates = carPrices.stream().map(quotes ->
            new PriceUpdate(quotes.getModel(), quotes.getTotalPrice())
        ) .collect(Collectors.toList());
        //flowable is response so doOnError can be used and .forEach contain each record
        priceUpdateProducer.send(priceUpdates).doOnError(e -> log.error("Failed to produce:", e.getCause()))
        .forEach(eachRecord -> {
            log.debug("Record sent to price update topic offset is:  "+eachRecord.topic()+" "
            +eachRecord.offset());
        });
    }
}
