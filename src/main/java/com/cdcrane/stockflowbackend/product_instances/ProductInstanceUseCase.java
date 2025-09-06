package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.product_instances.dto.NewProductInstanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductInstanceUseCase {

    Page<ProductInstance> getProductInstancesByRootProductId(UUID productId, Pageable pageable);

    void storeNewProductInstances(List<NewProductInstanceDTO> productInstanceDTOs);
}
