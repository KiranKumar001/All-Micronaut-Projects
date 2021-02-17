package com.kafka.example;

import com.kafka.example.carprice.CarPrice;
import com.kafka.example.carprice.CarPriceProducer;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Singleton
@Requires(notEnv = Environment.TEST)
public class EventScheduler {

    //class to push msg into topic periodically

    private static final List<String> SYMBOLS = Arrays.asList("TESLA", "AUDI", "BENZ", "BMW");

    private final CarPriceProducer carPriceProducer;

    public EventScheduler(final CarPriceProducer carPriceProducer) {
        this.carPriceProducer = carPriceProducer;
    }

    //evry 10 seconds it sends a msg to topic
    @Scheduled(fixedDelay = "10s")
    void generate() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final CarPrice carPrice = new CarPrice(
                SYMBOLS.get(random.nextInt(0, SYMBOLS.size() - 1)),
                randomValue(random),
                randomValue(random)
        );
        log.debug("Generate car price {}: "+carPrice);
        carPriceProducer.send(carPrice.getModel(), carPrice);
    }

    private BigDecimal randomValue(final ThreadLocalRandom random) {
        return BigDecimal.valueOf(random.nextDouble(0, 1000));
    }

}
