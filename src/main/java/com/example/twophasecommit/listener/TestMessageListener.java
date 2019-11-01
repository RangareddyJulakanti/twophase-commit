package com.example.twophasecommit.listener;

import com.example.twophasecommit.entity.Score;
import com.example.twophasecommit.error.BaseException;
import com.example.twophasecommit.error.PDMEdgeExceptionHandler;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by jerrie on 17-5-18.
 */
@Component
public class TestMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMessageListener.class);


    @JmsListener(destination = "scores",containerFactory = "myFactory")
    @Transactional(propagation = Propagation.REQUIRED)
    @PDMEdgeExceptionHandler
    public void onMessage(@Payload ActiveMQTextMessage events, @Headers Map<String, Object> headers) {
        LOGGER.info(events.toString());
        throw  BaseException.builder()
               .errorCode("500")
               .errorMessage("Message processing fail")
               .eventType("Score")
               .queueName("scores")
               .build();
    }

}
