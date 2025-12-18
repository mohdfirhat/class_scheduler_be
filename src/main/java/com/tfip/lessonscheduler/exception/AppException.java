package com.tfip.lessonscheduler.exception;

/**
 * Exception for unexpected developer errors
 */
public class AppException extends RuntimeException {

    /**
     * Constructs a new AppException with the specified detail message. This
     * exception is intended to signal unexpected developer errors during
     * application execution.
     *
     * @param message the detail message indicating the specifics of the
     *                developer error
     */
    public AppException(String message) {
        super(message);
    }
}
