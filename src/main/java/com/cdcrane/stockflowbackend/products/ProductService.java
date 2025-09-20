package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.authentication.SecurityUtils;
import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.products.categories.Category;
import com.cdcrane.stockflowbackend.products.categories.CategoryRepository;
import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductSearchResultDTO;
import com.cdcrane.stockflowbackend.users.ApplicationUser;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepo;
    private final SecurityUtils securityUtils;
    private final EntityManager em;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, SecurityUtils securityUtils, EntityManager em, CategoryRepository categoryRepository) {
        this.productRepo = productRepository;
        this.securityUtils = securityUtils;
        this.em = em;
        this.categoryRepository = categoryRepository;
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
    public List<ProductSearchResultDTO> searchByName(String name) {

        List<Product> products = productRepo.searchByName(name, PageRequest.of(0, 5));

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for name starting with " + name);
        }

        return products.stream().map(p -> new ProductSearchResultDTO(p.getName(), p.getFactoryName())).toList();

    }

    @Override
    public void createProduct(CreateProductDTO dto) {

        ApplicationUser currentUser = securityUtils.getCurrentAuth();

        Product product = Product.builder()
                .category(em.getReference(Category.class, dto.categoryId()))
                .name(dto.name().toLowerCase())
                .factoryName(dto.factoryName())
                .createdBy(currentUser)
                .groupByColour(dto.groupByColour())
                .groupByHeight(dto.groupByHeight())
                .groupByLength(dto.groupByLength())
                .groupByWidth(dto.groupByWidth())
                .build();

        productRepo.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, CreateProductDTO product) {

        Product toUpdate = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));

        toUpdate.setName(product.name().toLowerCase());
        toUpdate.setFactoryName(product.factoryName());
        toUpdate.setGroupByColour(product.groupByColour());
        toUpdate.setGroupByHeight(product.groupByHeight());
        toUpdate.setGroupByLength(product.groupByLength());
        toUpdate.setGroupByWidth(product.groupByWidth());

        // Only update category if it changed.
        if (product.categoryId() != toUpdate.getCategory().getId() ) {

            Category newCategory = categoryRepository.findById(product.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + product.categoryId() + " not found"));

            toUpdate.setCategory(newCategory);

        }

        return productRepo.save(toUpdate);

    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {

        Product toDelete = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));

        // Cascade REMOVE handles deleting all instances of the product.

        productRepo.delete(toDelete);

    }
}
