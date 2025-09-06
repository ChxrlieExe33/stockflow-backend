package com.cdcrane.stockflowbackend.product_instances;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductInstanceUseCase {

    Page<ProductInstance> getProductInstancesByRootProductId(UUID productId, Pageable pageable);
}
