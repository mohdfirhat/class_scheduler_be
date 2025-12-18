package com.tfip.lessonscheduler.exception;

/**
 * Exception for teacher/section status conflict between the front end and back
 * end
 */
public class StatusConflictException extends RuntimeException {

    /**
     * Constructs a new StatusConflictException with the specified detail
     * message. This exception is used to indicate a conflict in section or
     * teacher statuses between the front-end and back-end systems.
     *
     * @param message the detail message describing the specific conflict
     */
    public StatusConflictException(String message) {
        super(message);
    }
}
