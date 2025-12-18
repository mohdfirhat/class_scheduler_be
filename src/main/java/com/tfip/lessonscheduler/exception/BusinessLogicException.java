package com.tfip.lessonscheduler.exception;

/**
 * Exception for business logic errors
 */
public class BusinessLogicException extends RuntimeException {

    /**
     * Constructs a new BusinessLogicException with the specified detail
     * message. This exception is used to indicate that a business logic error
     * has occurred during application execution.
     *
     * @param message the detail message describing the specific error in
     *                business logic
     */
    public BusinessLogicException(String message) {
        super(message);
    }
}
