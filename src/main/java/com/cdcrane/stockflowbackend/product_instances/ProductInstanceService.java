package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductInstanceService implements ProductInstanceUseCase {

    private final ProductInstanceRepository productInstanceRepo;

    public ProductInstanceService(ProductInstanceRepository productInstanceRepository) {
        this.productInstanceRepo = productInstanceRepository;
    }

    @Override
    public Page<ProductInstance> getProductInstancesByRootProductId(UUID productId, Pageable pageable) {

        Page<ProductInstance> instances = productInstanceRepo.findByProductId(productId, pageable);

        if (instances.isEmpty()) {

            throw new ResourceNotFoundException("No instances found for product with ID " + productId);
        }

        return instances;

    }

}
