package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductInstanceRepository extends JpaRepository<ProductInstance, UUID>,JpaSpecificationExecutor<ProductInstance> {

    // Find instances of a root product.
    Page<ProductInstance> findByProductId(UUID productId, Pageable pageable);

    @Query("""
        SELECT NEW com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceCountDTO(
                i.width,
                i.length,
                null,
                i.colour,
                COUNT(i)
        ) FROM ProductInstance i WHERE i.product.id = ?1 AND i.reserved = false GROUP BY i.width, i.length, i.colour
    """)
    List<ProductInstanceCountDTO> getCountByProductIdGroupByWidthLengthColour(UUID productId);

    @Query("""
        SELECT NEW com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceCountDTO(
                i.width,
                i.length,
                null,
                null,
                COUNT(i)
        ) FROM ProductInstance i WHERE i.product.id = ?1 AND i.reserved = false GROUP BY i.width, i.length
    """)
    List<ProductInstanceCountDTO> getCountByProductIdGroupByWidthLength(UUID productId);

    @Query("""
        SELECT NEW com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceCountDTO(
                null,
                null,
                null,
                i.colour,
                COUNT(i)
        ) FROM ProductInstance i WHERE i.product.id = ?1 AND i.reserved = false GROUP BY i.colour
    """)
    List<ProductInstanceCountDTO> getCountByProductIdGroupByColour(UUID productId);

    List<ProductInstance> findByIdIn(List<UUID> ids);

}
