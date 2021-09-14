package com.soumen.pubsubpoc;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author Soumen Karmakar
 * @Date 14/09/2021
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class Tester {
    private final Publisher publisher;

    @Scheduled(fixedRate = 5000)
    public void testPublish() {
        String message = "TEST_" + Instant.now();
        log.info("Publishing : {}", message);
        publisher.publishMessage(message);
    }
}
