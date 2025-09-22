package com.cdcrane.stockflowbackend.products.dto;

import java.util.UUID;

public record ProductSearchResultDTO(String name, String factoryName, UUID productId) {
}
