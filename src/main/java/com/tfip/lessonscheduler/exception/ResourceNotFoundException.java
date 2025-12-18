package com.tfip.lessonscheduler.exception;

/**
 * Exception for resource not found errors
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail
     * message. This exception is used to indicate that a requested resource
     * could not be found.
     *
     * @param message the detail message providing additional information about
     *                the missing resource
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
