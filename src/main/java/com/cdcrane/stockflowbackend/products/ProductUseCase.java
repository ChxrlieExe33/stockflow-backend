package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;

import java.util.UUID;

public interface ProductUseCase {

    ProductDTO getProductById(UUID productId);

    void createProduct(CreateProductDTO product);
}
