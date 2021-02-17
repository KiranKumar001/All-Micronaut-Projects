package com.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
public class TestContainerSetupTest {

    /*@Rule
    public KafkaContainer kafka = new KafkaContainer();*/

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
    //testcontainer version in build.gradle is imp or
    // it will try to pull the image everytime
    // reference: https://www.testcontainers.org/features/advanced_options/


    @Test
    void setupWorks() {
        kafka.start();
        log.debug("Bootstrap Servers: {} "+ kafka.getBootstrapServers());
        kafka.stop();
    }

}
