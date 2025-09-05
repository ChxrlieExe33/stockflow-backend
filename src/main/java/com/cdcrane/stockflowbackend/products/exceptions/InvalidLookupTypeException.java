package com.cdcrane.stockflowbackend.products.exceptions;

public class InvalidLookupTypeException extends RuntimeException{

    public InvalidLookupTypeException(String message) {
        super(message);
    }
}
