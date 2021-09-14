package com.soumen.pubsubpoc;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
@Component
interface PubSubOutboundGateway {
    void sendToPubSub(String message);
}

/**
 * @author Soumen Karmakar
 * @Date 14/09/2021
 */
@Configuration
@Log4j2
public class PubSubChannelConfig {
    @Value("${topic_name:TEST_TOPIC}")
    private String topicName;
    @Value("${subscriber.name}")
    private String subName;

    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubSubTemplate) {
        return new PubSubMessageHandler(pubSubTemplate, topicName);
    }

    @Bean
    public MessageChannel pubSubInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubSubInputChannel")
                    MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subName);
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.AUTO_ACK);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "pubSubInputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
            String str = new String((byte[]) message.getPayload());
            log.info("Message consumed : {}", str);
            /*BasicAcknowledgeablePubsubMessage originalMsg = message.getHeaders()
                    .get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMsg.ack();*/
        };
    }
}
