package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID productId) {

        var response = productUseCase.getProductById(productId);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductDTO>> getByCategoryId(@PathVariable UUID categoryId, Pageable pageable) {

        var response = productUseCase.getByCategoryId(categoryId, pageable);

        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody CreateProductDTO product) {

        productUseCase.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
