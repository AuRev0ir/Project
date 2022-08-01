package com.spring.app.rest.errorHandler;


import com.spring.app.exception.CreateEntityException;
import com.spring.app.exception.UpdateEntityException;
import com.spring.app.exception.NotFoundEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

    private Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<Object> handlerNotFoundEntityException(NotFoundEntityException ex){

        Map<String, Object> body = new HashMap<>();

        body.put("error", "Entity Not Found" );
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        logger.error(message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateEntityException.class)
    public ResponseEntity<Object> handlerCreateEntityException(CreateEntityException ex){

        Map<String, Object> body = new HashMap<>();

        body.put("error", "Entity Not Created");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        logger.error(message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateEntityException.class)
    public ResponseEntity<Object> handlerUpdateEntityException(UpdateEntityException ex){
        Map<String, Object> body = new HashMap<>();

        body.put("error", "Entity Not Updated");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        logger.error(message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
