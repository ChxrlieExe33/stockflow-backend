package com.cdcrane.stockflowbackend.product_instances.dto;

import java.time.Instant;
import java.util.UUID;

public record ProductInstanceDTO(UUID instanceId, UUID rootProductId, Integer width, Integer length, Integer height, String colour, boolean reserved, Instant savedAt) {
}
