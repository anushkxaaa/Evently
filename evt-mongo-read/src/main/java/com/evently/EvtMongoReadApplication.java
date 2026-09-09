package com.evently;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class EvtMongoReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvtMongoReadApplication.class, args);
    }
}