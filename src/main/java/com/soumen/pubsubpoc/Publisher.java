package com.soumen.pubsubpoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

/**
 * @author Soumen Karmakar
 * @Date 14/09/2021
 */
@Component
public class Publisher {
    @Autowired
    private PubSubOutboundGateway messagingGateway;

    public void publishMessage(String message){
        this.messagingGateway.sendToPubSub(message);
    }
}