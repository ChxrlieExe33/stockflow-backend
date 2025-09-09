package com.cdcrane.stockflowbackend.product_instances.exceptions;

public class ItemAlreadyReservedException extends RuntimeException{

    public ItemAlreadyReservedException(String message) {
        super(message);
    }
}
