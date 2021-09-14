package com.soumen.pubsubpoc;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Log4j2
@EnableScheduling
public class PubSubPocApplication {
    public static void main(String[] args) {
        SpringApplication.run(PubSubPocApplication.class, args);
    }

}

