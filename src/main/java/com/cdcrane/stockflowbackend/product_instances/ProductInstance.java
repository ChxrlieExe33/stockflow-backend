package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "product_instances")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_instance_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = true)
    private Integer width;

    @Column(nullable = true)
    private Integer length;

    @Column(nullable = true)
    private Integer height;

    @Column(nullable = true)
    private String colour;

    private boolean reserved;

    // Add the FK to the product it could be reserved for.

    @CreatedDate
    private Instant savedAt;

}
