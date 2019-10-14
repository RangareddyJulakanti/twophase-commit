package com.example.twophasecommit.repository;

import com.example.twophasecommit.entity.Score;
import com.google.gson.Gson;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JmsRepository {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ActiveMQQueue queue;

    @Autowired
    private Gson gson;

    public void sendScoreMessage(Score score) {
        jmsTemplate.execute((session, messageProducer) -> {
            ActiveMQTextMessage message = new ActiveMQTextMessage();
            message.setText(gson.toJson(score));
            messageProducer.send(queue, message);
            return null;
        });
    }

}
