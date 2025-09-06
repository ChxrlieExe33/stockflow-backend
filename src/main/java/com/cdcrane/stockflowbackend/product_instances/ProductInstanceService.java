package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.product_instances.dto.NewProductInstanceDTO;
import com.cdcrane.stockflowbackend.products.Product;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductInstanceService implements ProductInstanceUseCase {

    private final ProductInstanceRepository productInstanceRepo;
    private final EntityManager em;

    public ProductInstanceService(ProductInstanceRepository productInstanceRepository, EntityManager em) {
        this.productInstanceRepo = productInstanceRepository;
        this.em = em;
    }

    @Override
    public Page<ProductInstance> getProductInstancesByRootProductId(UUID productId, Pageable pageable) {

        Page<ProductInstance> instances = productInstanceRepo.findByProductId(productId, pageable);

        if (instances.isEmpty()) {

            throw new ResourceNotFoundException("No instances found for product with ID " + productId);
        }

        return instances;

    }

    @Override
    @Transactional
    public void storeNewProductInstances(List<NewProductInstanceDTO> productInstanceDTOs) {

        List<ProductInstance> instances = productInstanceDTOs.stream()
                .map(dto -> ProductInstance.builder()
                        .product(em.getReference(Product.class, dto.rootProductId()))
                        .width(dto.width())
                        .length(dto.length())
                        .height(dto.height())
                        .colour(dto.colour())
                        .reserved(dto.reserved())
                        .build())
                .toList();

        productInstanceRepo.saveAll(instances);

    }

}
