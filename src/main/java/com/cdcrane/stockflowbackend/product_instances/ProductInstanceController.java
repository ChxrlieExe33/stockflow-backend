package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.product_instances.dto.NewProductInstanceDTO;
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
    public Page<ProductInstanceDTO> getProductInstancesByRootProductId(@PathVariable UUID rootProdId, Pageable pageable) {

        Page<ProductInstance> instances = productInstanceUseCase.getProductInstancesByRootProductId(rootProdId, pageable);

        return instances.map(i ->
                new ProductInstanceDTO(i.getId(), i.getProduct().getId(),
                        i.getWidth(), i.getLength(), i.getHeight(),
                        i.getColour(), i.isReserved(), i.getSavedAt()));

    }

    @PostMapping
    public ResponseEntity<Void> storeNewProductInstances(@RequestBody List<NewProductInstanceDTO> productInstanceDTOs) {

        productInstanceUseCase.storeNewProductInstances(productInstanceDTOs);

        return ResponseEntity.ok().build();

    }

}
