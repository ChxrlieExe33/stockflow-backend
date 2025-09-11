package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.products.categories.CategoryUseCase;
import com.cdcrane.stockflowbackend.products.categories.dto.CategoryDTO;
import com.cdcrane.stockflowbackend.products.categories.dto.CreateCategoryDTO;
import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductSearchResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductUseCase productUseCase;
    private final CategoryUseCase categoryUseCase;

    public ProductController(ProductUseCase productUseCase, CategoryUseCase categoryUseCase) {
        this.productUseCase = productUseCase;
        this.categoryUseCase = categoryUseCase;
    }

    // ------------------------------------------------------------------------
    // ------------------------------- PRODUCTS -------------------------------
    // ------------------------------------------------------------------------

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

    @GetMapping("/search-summary/{name}")
    public ResponseEntity<List<ProductSearchResultDTO>> getFirst5ByStartOfName(@PathVariable String name) {

        var response = productUseCase.getFirst5ByStartOfName(name);

        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody CreateProductDTO product) {

        productUseCase.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID productId, @RequestBody CreateProductDTO product) {

        Product result = productUseCase.updateProduct(productId, product);

        return ResponseEntity.ok(mapProductToProductDto(result));

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {

        productUseCase.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


    // ------------------------------------------------------------------------
    // ------------------------------- CATEGORIES -----------------------------
    // ------------------------------------------------------------------------

    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(Pageable pageable) {

        var response = categoryUseCase.getAllCategories(pageable).map(c -> new CategoryDTO(c.getId(), c.getName()));

        return ResponseEntity.ok(response);

    }

    @PostMapping("/categories")
    public ResponseEntity<Void> createCategory(@RequestBody CreateCategoryDTO category) {

        categoryUseCase.createCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }


    // --------------------------------------------------------------------------------
    // -------------------------------- HELPER METHODS --------------------------------
    // --------------------------------------------------------------------------------

    private ProductDTO mapProductToProductDto(Product product) {

        return new ProductDTO(product.getId(), product.getName(), product.getFactoryName(),
                product.isGroupByWidth(), product.isGroupByLength(), product.isGroupByHeight(), product.isGroupByColour(),
                product.getCreatedAt(), product.getCreatedBy().getUsername());
    }

}
