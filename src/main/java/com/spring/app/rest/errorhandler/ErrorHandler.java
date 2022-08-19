package com.spring.app.rest.errorhandler;


import com.spring.app.exception.EntityNotCreatedException;
import com.spring.app.exception.EntityNotUpdateException;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.rest.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ErrorDto> handlerNotFoundEntityException(NotFoundEntityException ex){
        var errorMsg = new ErrorDto();
        errorMsg.setError("Entity Not Found");
        errorMsg.setMessage(ex.getMessage());
        errorMsg.setTimestamp(LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        log.error(message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
    }

    @ExceptionHandler(EntityNotCreatedException.class)
    public ResponseEntity<ErrorDto> handlerCreateEntityException(EntityNotCreatedException ex){
        var errorMsg = new ErrorDto();
        errorMsg.setError("Entity Not Created");
        errorMsg.setMessage(ex.getMessage());
        errorMsg.setTimestamp(LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        log.error(message);

        return ResponseEntity.badRequest().body(errorMsg);
    }

    @ExceptionHandler(EntityNotUpdateException.class)
    public ResponseEntity<Object> handlerUpdateEntityException(EntityNotUpdateException ex){
        var errorMsg = new ErrorDto();
        errorMsg.setError("Entity Not Updated");
        errorMsg.setMessage(ex.getMessage());
        errorMsg.setTimestamp(LocalDateTime.now());

        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        log.error(message);

        return ResponseEntity.badRequest().body(errorMsg);
    }
}
