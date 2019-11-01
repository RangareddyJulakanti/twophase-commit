package com.example.twophasecommit.error;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class JMSListenerAdvice {
    @AfterThrowing(pointcut ="@annotation(PDMEdgeExceptionHandler)",throwing = "exception")
    public void handleExceptions(JoinPoint joinPoint,Throwable exception){
     if(exception instanceof BaseException){
         BaseException baseException= convertInstanceOfObject(exception,BaseException.class);
         log.error("ERROR_CODE:: {}",baseException.getErrorCode());
         log.error("ERROR_MESSAGE: {}",baseException.getErrorMessage());
         log.error("ERROR_CODE: {}",baseException.getEventType());
         log.error("ERROR_CODE: {}",baseException.getQueueName());
     }

    }
    private static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch(ClassCastException e) {
            return null;
        }
    }
}
