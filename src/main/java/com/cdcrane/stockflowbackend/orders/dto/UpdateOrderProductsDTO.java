package com.cdcrane.stockflowbackend.orders.dto;

import java.util.List;
import java.util.UUID;

public record UpdateOrderProductsDTO(List<UUID> toRemoveProductInstanceIds, List<UUID> toAddProductInstanceIds) {
}
