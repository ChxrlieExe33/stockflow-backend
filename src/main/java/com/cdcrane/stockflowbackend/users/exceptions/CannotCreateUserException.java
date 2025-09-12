package com.cdcrane.stockflowbackend.users.exceptions;

public class CannotCreateUserException extends RuntimeException{

    public CannotCreateUserException(String message){
        super(message);
    }
}
