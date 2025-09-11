package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductSearchResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductUseCase {

    ProductDTO getProductById(UUID productId);

    Page<ProductDTO> getByCategoryId(UUID categoryId, Pageable pageable);

    List<ProductSearchResultDTO> getFirst5ByStartOfName(String name);

    void createProduct(CreateProductDTO product);

    Product updateProduct(UUID productId, CreateProductDTO product);

    void deleteProduct(UUID productId);
}
