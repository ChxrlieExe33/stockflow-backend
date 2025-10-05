package com.cdcrane.stockflowbackend.product_instances.exceptions;

public class ItemsDoNotBelongToSaleException extends RuntimeException{
    public ItemsDoNotBelongToSaleException(String message) {
        super(message);
    }
}
