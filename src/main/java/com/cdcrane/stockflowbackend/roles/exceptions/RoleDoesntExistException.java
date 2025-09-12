package com.cdcrane.stockflowbackend.roles.exceptions;

public class RoleDoesntExistException extends RuntimeException{

    public RoleDoesntExistException(String message) {
        super(message);
    }
}
