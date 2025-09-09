package com.cdcrane.stockflowbackend.orders.dto;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public record OrderDTO(UUID orderId, String reference, Date deliveryDate, String deliveryAddress, String deliveryPhoneNumber, String extraInformation, Instant orderedAt, String soldBy) {
}
