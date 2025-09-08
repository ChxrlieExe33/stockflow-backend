package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.product_instances.dto.NewProductInstanceDTO;
import com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceCountDTO;
import com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-instances")
public class ProductInstanceController {

    private final ProductInstanceUseCase productInstanceUseCase;

    public ProductInstanceController(ProductInstanceUseCase productInstanceUseCase) {
        this.productInstanceUseCase = productInstanceUseCase;
    }

    @GetMapping("/by-root-product/{rootProdId}")
    public ResponseEntity<Page<ProductInstanceDTO>> getProductInstancesByRootProductId(@PathVariable UUID rootProdId, Pageable pageable) {

        Page<ProductInstance> instances = productInstanceUseCase.getProductInstancesByRootProductId(rootProdId, pageable);

        var result = instances.map(i ->
                new ProductInstanceDTO(i.getId(), i.getProduct().getId(),
                        i.getWidth(), i.getLength(), i.getHeight(),
                        i.getColour(), i.isReserved(), i.getSavedAt()));

        return ResponseEntity.ok(result);

    }

    @GetMapping("/search-by-product-id/{rootProdId}")
    public ResponseEntity<List<ProductInstanceDTO>> getProductInstancesByRootProductIdWithFilters(@PathVariable UUID rootProdId, @RequestParam(required = false) Integer width,
                                                                                                  @RequestParam(required = false) Integer length, @RequestParam(required = false) Integer height,
                                                                                                  @RequestParam(required = false) String colour) {

        var result = productInstanceUseCase.getProductInstancesByRootProductIdWithFilters(rootProdId, width, length, height, colour);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/count-by-product/{productId}")
    public ResponseEntity<List<ProductInstanceCountDTO>> getCountsByProductId(@PathVariable UUID productId) {

        var result = productInstanceUseCase.getCountsByProductId(productId);

        return ResponseEntity.ok(result);

    }


    @PostMapping
    public ResponseEntity<Void> storeNewProductInstances(@RequestBody List<NewProductInstanceDTO> productInstanceDTOs) {

        productInstanceUseCase.storeNewProductInstances(productInstanceDTOs);

        return ResponseEntity.ok().build();

    }

}
