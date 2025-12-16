package com.tfip.lessonscheduler.advise;

import com.tfip.lessonscheduler.exception.StatusConflictException;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({
    ResourceNotFoundException.class
  })
  public ResponseEntity<String> handleNotFoundException(RuntimeException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    IllegalArgumentException.class
  })
  public ResponseEntity<String> handleBadRequestException(RuntimeException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler({
    Exception.class
  })
  public ResponseEntity<String> handleGlobalException(Exception e) {
    return new ResponseEntity<>("Something is wrong with the server", HttpStatus.INTERNAL_SERVER_ERROR);
  }

    @ExceptionHandler({
    StatusConflictException.class
    })
    public ResponseEntity<String> handleStatusConflictException(StatusConflictException e) {
        return new ResponseEntity<>(e.getMessage() , HttpStatus.CONFLICT);
    }
}
