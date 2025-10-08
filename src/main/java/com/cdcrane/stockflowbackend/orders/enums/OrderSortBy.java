package com.cdcrane.stockflowbackend.orders.enums;

public enum OrderSortBy {
    DELIVERY,
    ORDERED;

    public static OrderSortBy fromString(String value) {

        if (value == null) return ORDERED;
        try {
            return OrderSortBy.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ORDERED;
        }
    }
}
