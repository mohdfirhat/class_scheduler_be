package com.tfip.lessonscheduler.advise;

import com.tfip.lessonscheduler.exception.StatusConflictException;
import com.tfip.lessonscheduler.exception.BusinessLogicException;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Response for Resource Not Found Exception with HTTP status 404 <br/>
   * @return ResponseEntity with a String with the error message
   */
  @ExceptionHandler({
    ResourceNotFoundException.class
  })
  public ResponseEntity<String> handleNotFoundException(RuntimeException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Response for Bad Request Exception with HTTP status 400 <br/>
   * Used for Business Logic
   * @return ResponseEntity with a String with the error message
   */
  @ExceptionHandler({
    IllegalArgumentException.class,
    BusinessLogicException.class
  })
  public ResponseEntity<String> handleBadRequestException(RuntimeException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Response for All Conflict Exception with HTTP status 409 <br/>
   * @return ResponseEntity with a String with the error message
   */
  @ExceptionHandler({
  StatusConflictException.class
  })
  public ResponseEntity<String> handleStatusConflictException(StatusConflictException e) {
      return new ResponseEntity<>(e.getMessage() , HttpStatus.CONFLICT);
  }

  /**
   * Response for All Wrong API URL Exception with HTTP status 404 <br/>
   * @return ResponseEntity with a String with the error message
   */
  @ExceptionHandler({
    NoResourceFoundException.class
  })
  public ResponseEntity<String> handleInvalidURIException(Exception e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Response for All Uncaught Exception with HTTP status 500 <br/>
   * @return ResponseEntity with a String with the error message
   */
  @ExceptionHandler({
    Exception.class
  })
  public ResponseEntity<String> handleGlobalException(Exception e) {
    e.printStackTrace();
    return new ResponseEntity<>("Something is wrong with the server", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
