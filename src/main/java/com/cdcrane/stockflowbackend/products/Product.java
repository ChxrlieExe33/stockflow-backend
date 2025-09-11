package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.product_instances.ProductInstance;
import com.cdcrane.stockflowbackend.products.categories.Category;
import com.cdcrane.stockflowbackend.users.ApplicationUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID id;

    private String name;

    @Column(name = "factory_name")
    private String factoryName;

    private boolean groupByWidth;

    private boolean groupByLength;

    private boolean groupByHeight;

    private boolean groupByColour;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private ApplicationUser createdBy;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private List<ProductInstance> productInstances;
}
