package com.cdcrane.stockflowbackend.products.dto;

import java.util.UUID;

public record CreateProductDTO(String name, String factoryName, boolean groupByWidth, boolean groupByLength, boolean groupByHeight, boolean groupByColour, UUID categoryId) {
}
