package com.cdcrane.stockflowbackend.products.dto;

import java.util.UUID;

public record CreateProductDTO(String name, String factoryName, String lookupType, UUID categoryId) {
}
