package com.cdcrane.stockflowbackend.orders.dto;

import java.util.Date;

public record   UpdateOrderDTO(String reference, Date deliveryDate, String deliveryAddress, String deliveryPhoneNumber, String extraInformation, boolean delivered) {
}
