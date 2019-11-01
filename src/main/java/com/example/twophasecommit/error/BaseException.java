package com.example.twophasecommit.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
    private String eventType;
    private String queueName;
    public static class BaseExceptionBuilder{

    }
}
