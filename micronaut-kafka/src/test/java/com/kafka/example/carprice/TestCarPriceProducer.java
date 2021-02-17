package com.kafka.example.carprice;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Testcontainers
public class TestCarPriceProducer {

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    private static ApplicationContext context;

    private static final String PROPERTY_NAME = "TestCarPriceProducer";

    final CarPriceObserver observer = context.getBean(CarPriceObserver.class);

    @BeforeAll
    static void start() {
        kafka.start();
        log.debug("Bootstrap Servers: {}", kafka.getBootstrapServers());

        //specify property name as class name so that its available only for this class
        context = ApplicationContext.run(
                CollectionUtils.mapOf(
                        "kafka.bootstrap.servers", kafka.getBootstrapServers(),
                        PROPERTY_NAME, StringUtils.TRUE
                ),
                Environment.TEST
        );
    }

    @AfterAll
    static void stop() {
        kafka.stop();
        context.close();
    }

    @Test
    void producing10RecordsWorks() {

        final CarPriceProducer producer = context.getBean(CarPriceProducer.class);
        final ThreadLocalRandom random = ThreadLocalRandom.current();

        //run for 10times
        IntStream.range(0, 10).forEach(count -> {
            producer.send("TEST-" + count, new CarPrice(
                    "TEST-" + count,
                    randomValue(random),
                    randomValue(random)
            ));
        });
        Awaitility.await().untilAsserted(() -> {
            assertEquals(10, observer.inspected.size());
        });

    }

    private BigDecimal randomValue(final ThreadLocalRandom random) {
        return BigDecimal.valueOf(random.nextDouble(0, 1000));
    }

    //listener class, offset is earliest to consume recent msgs
    @KafkaListener(
            clientId = "car-price-observer",
            offsetReset = OffsetReset.EARLIEST
    )
    @Requires(env = Environment.TEST)
    @Requires(property = PROPERTY_NAME, value = StringUtils.TRUE)
    public static class CarPriceObserver {

        List<CarPrice> inspected = new ArrayList<>();

        @Topic("car-price")
        void receive(List<CarPrice> carPrices) {
            log.debug("Consumed: {}", carPrices);
            inspected.addAll(carPrices);
        }
    }
}
