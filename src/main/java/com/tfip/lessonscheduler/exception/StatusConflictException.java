package com.tfip.lessonscheduler.exception;

public class StatusConflictException extends RuntimeException {
    public StatusConflictException(String message) {
        super(message);
    }
}
