package com.cdcrane.stockflowbackend.product_instances;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductInstanceRepository extends JpaRepository<ProductInstance, UUID> {

    // Find instances of a root product.
    Page<ProductInstance> findByProductId(UUID productId, Pageable pageable);
}
