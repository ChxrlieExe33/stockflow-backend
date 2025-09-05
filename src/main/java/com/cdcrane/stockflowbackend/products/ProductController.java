package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;
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
    public ProductDTO getProductById(@PathVariable UUID productId) {

        return productUseCase.getProductById(productId);

    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody CreateProductDTO product) {

        productUseCase.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
