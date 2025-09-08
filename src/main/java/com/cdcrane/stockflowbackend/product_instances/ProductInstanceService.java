package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.product_instances.dto.NewProductInstanceDTO;
import com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceCountDTO;
import com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceDTO;
import com.cdcrane.stockflowbackend.products.Product;
import com.cdcrane.stockflowbackend.products.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductInstanceService implements ProductInstanceUseCase {

    private final ProductInstanceRepository productInstanceRepo;
    private final EntityManager em;
    private final ProductRepository productRepo;

    public ProductInstanceService(ProductInstanceRepository productInstanceRepository, EntityManager em, ProductRepository productRepository) {
        this.productInstanceRepo = productInstanceRepository;
        this.em = em;
        this.productRepo = productRepository;
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
                        .colour(dto.colour().toLowerCase())
                        .reserved(dto.reserved())
                        .build())
                .toList();

        productInstanceRepo.saveAll(instances);

    }

    @Override
    public List<ProductInstanceCountDTO> getCountsByProductId(UUID productId) {

        Product root = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));

        List<ProductInstanceCountDTO> counts;

        if(root.isGroupByWidth() && root.isGroupByLength() && root.isGroupByColour()) {

            counts = productInstanceRepo.getCountByProductIdGroupByWidthLengthColour(productId);

        } else if (root.isGroupByWidth() && root.isGroupByLength()) {

            counts = productInstanceRepo.getCountByProductIdGroupByWidthLength(productId);

        } else if (root.isGroupByColour()) {

            counts = productInstanceRepo.getCountByProductIdGroupByColour(productId);

        } else {

            counts = List.of();
        }

        return counts;

    }

    @Override
    public List<ProductInstanceDTO> getProductInstancesByRootProductIdWithFilters(UUID rootProductId, Integer width, Integer length, Integer height, String colour) {

        String lowerCaseColour = colour == null ? null : colour.toLowerCase();

        // Using a specification to build a query based on what parameters were provided, null parameters are not added to the final query.
        Specification<ProductInstance> spec = ProductInstanceSpecifications.byRootProductId(rootProductId)
                .and(ProductInstanceSpecifications.hasWidth(width))
                .and(ProductInstanceSpecifications.hasLength(length))
                .and(ProductInstanceSpecifications.hasHeight(height))
                .and(ProductInstanceSpecifications.hasColour(lowerCaseColour))
                .and(ProductInstanceSpecifications.byReserved(false));

        List<ProductInstance> instances = productInstanceRepo.findAll(spec);

        if (instances.isEmpty()) {
            throw new ResourceNotFoundException("No available instances found for product with ID " + rootProductId);
        }

        return instances.stream()
                .map(i ->
                        new ProductInstanceDTO(i.getId(), rootProductId, i.getWidth(), i.getLength(), i.getHeight(), i.getColour(), i.isReserved(), i.getSavedAt())
                )
                .toList();


    }

}
