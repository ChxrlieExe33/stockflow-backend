package com.cdcrane.stockflowbackend.orders.dto;

import java.util.UUID;

public record OrderProductInstanceProjection(UUID instanceId, String productName, Integer width, Integer length, Integer height, String colour) {
}
