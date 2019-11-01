package com.example.twophasecommit.error;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PDMEdgeExceptionHandler {
}
