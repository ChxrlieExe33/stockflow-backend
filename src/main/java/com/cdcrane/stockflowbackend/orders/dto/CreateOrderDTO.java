package com.cdcrane.stockflowbackend.orders.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CreateOrderDTO(String reference, Date deliveryDate, String deliveryAddress, String deliveryPhoneNumber, String extraInformation, List<UUID> productInstanceIds) {
}
