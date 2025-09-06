package com.cdcrane.stockflowbackend.products.dto;

import java.time.Instant;
import java.util.UUID;

public record ProductDTO(UUID productId,String name, String factoryName, String categoryName, UUID categoryId, boolean groupByWidth, boolean groupByLength, boolean groupByHeight, boolean groupByColour, Instant createdAt, String usernameCreatedBy) {
}
