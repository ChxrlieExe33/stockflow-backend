package com.cdcrane.stockflowbackend.product_instances.dto;

import java.util.UUID;

public record NewProductInstanceDTO(UUID rootProductId, Integer width, Integer length, Integer height, String colour, boolean reserved) {
}
