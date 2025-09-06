package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductUseCase {

    ProductDTO getProductById(UUID productId);

    Page<ProductDTO> getByCategoryId(UUID categoryId, Pageable pageable);

    void createProduct(CreateProductDTO product);
}
