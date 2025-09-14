package com.cdcrane.stockflowbackend.authentication.exceptions;

public class BadLoginException extends RuntimeException{

    public BadLoginException(String message) {
        super(message);
    }
}
