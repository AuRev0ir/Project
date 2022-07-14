package com.spring.app.rest.errorHandler;


import com.spring.app.exception.CreateException;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<String> handlerRepositoryException(RepositoryException ex){
        return ResponseEntity.badRequest().body("RepositoryException: NOT FOUND");
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handlerServiceException(ServiceException ex){
        return ResponseEntity.badRequest().body("ServiceException: SERVICE ERROR");
    }

    @ExceptionHandler(CreateException.class)
    public ResponseEntity<String> handlerCreateException(CreateException ex){
        return ResponseEntity.badRequest().body("CreateException: ERROR WHILE CREATING");
    }
}
