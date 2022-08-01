package com.spring.app.rest.errorhandler;


import com.spring.app.exception.EntityNotCreatedException;
import com.spring.app.exception.EntityNotUpdateException;
import com.spring.app.exception.NotFoundEntityException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<Object> handlerNotFoundEntityException(NotFoundEntityException ex){

        Map<String, Object> body = new HashMap<>();

        body.put("error", "Entity Not Found" );
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        log.error(message);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotCreatedException.class)
    public ResponseEntity<Object> handlerCreateEntityException(EntityNotCreatedException ex){

        Map<String, Object> body = new HashMap<>();

        body.put("error", "Entity Not Created");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        log.error(message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotUpdateException.class)
    public ResponseEntity<Object> handlerUpdateEntityException(EntityNotUpdateException ex){
        Map<String, Object> body = new HashMap<>();

        body.put("error", "Entity Not Updated");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        log.error(message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
