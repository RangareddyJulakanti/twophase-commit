package com.example.twophasecommit.listener;

import com.example.twophasecommit.config.JMSConfig;
import com.example.twophasecommit.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component("notifierTemplate")
public class NotifierTemplate {
    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    JMSConfig.MQProperties mqProperties;

    @Transactional(propagation = Propagation.REQUIRED)
    public void notify(Score score) {
        MessageBuilder<Score> messageBuilder = MessageBuilder
                .withPayload(score);
        messageBuilder.setHeader("request-Id", UUID.randomUUID().toString());
        Message<Score> message = messageBuilder.build();
        jmsMessagingTemplate.send("scores", message);
    }
}
