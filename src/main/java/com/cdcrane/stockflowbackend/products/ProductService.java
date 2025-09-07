package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.authentication.SecurityUtils;
import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.products.categories.Category;
import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;
import com.cdcrane.stockflowbackend.users.ApplicationUser;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepo;
    private final SecurityUtils securityUtils;
    private final EntityManager em;

    public ProductService(ProductRepository productRepository, SecurityUtils securityUtils, EntityManager em) {
        this.productRepo = productRepository;
        this.securityUtils = securityUtils;
        this.em = em;
    }

    @Override
    public ProductDTO getProductById(UUID productId) {

        Product prod = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));

        return new ProductDTO(prod.getId() ,prod.getName(), prod.getFactoryName(),
                prod.isGroupByWidth(), prod.isGroupByLength(), prod.isGroupByHeight(), prod.isGroupByColour(),
                prod.getCreatedAt(), prod.getCreatedBy().getUsername());

    }

    @Override
    public Page<ProductDTO> getByCategoryId(UUID categoryId, Pageable pageable) {

        Page<Product> products = productRepo.findByCategoryId(categoryId, pageable);

        if(products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category with ID " + categoryId);
        }

        return products.map(p -> new ProductDTO(p.getId(), p.getName(), p.getFactoryName(),
                p.isGroupByWidth(), p.isGroupByLength(), p.isGroupByHeight(), p.isGroupByColour(),
                p.getCreatedAt(), p.getCreatedBy().getUsername()));

    }

    @Override
    public void createProduct(CreateProductDTO dto) {

        ApplicationUser currentUser = securityUtils.getCurrentAuth();

        Product product = Product.builder()
                .category(em.getReference(Category.class, dto.categoryId()))
                .name(dto.name())
                .factoryName(dto.factoryName())
                .createdBy(currentUser)
                .groupByColour(dto.groupByColour())
                .groupByHeight(dto.groupByHeight())
                .groupByLength(dto.groupByLength())
                .groupByWidth(dto.groupByWidth())
                .build();

        productRepo.save(product);
    }
}
