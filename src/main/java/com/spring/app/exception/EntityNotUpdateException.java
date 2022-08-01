package com.spring.app.exception;

public class EntityNotUpdateException extends RuntimeException{

    public EntityNotUpdateException(String message) {
        super(message);
    }
}
