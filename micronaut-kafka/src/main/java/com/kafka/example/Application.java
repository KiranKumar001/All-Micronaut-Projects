package com.kafka.example;

import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
        //also note all kafka operations are blocking and
        // can see pool-1-thread-1 being used in logs,
        // not the event threads which are non blocking,
        // micronaut internally took care of it
    }
}
