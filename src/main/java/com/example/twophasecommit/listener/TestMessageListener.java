package com.example.twophasecommit.listener;

import com.atomikos.icatch.event.Event;
import com.example.twophasecommit.entity.Score;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * Created by jerrie on 17-5-18.
 */
@Component
public class TestMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMessageListener.class);


    @JmsListener(destination = "scores",containerFactory = "myFactory")
    @Transactional(propagation = Propagation.REQUIRED)
    public void onMessage(@Payload ActiveMQTextMessage events, @Headers Map<String, Object> headers) {
        LOGGER.info(events.toString());
      // throw new RuntimeException(new JMSException("error"));


    }

}
