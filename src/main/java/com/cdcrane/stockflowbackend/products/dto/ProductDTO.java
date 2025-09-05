package com.cdcrane.stockflowbackend.products.dto;

import java.time.Instant;
import java.util.UUID;

public record ProductDTO(String name, String factoryName, String categoryName, UUID categoryId, Instant createdAt, String usernameCreatedBy) {
}
