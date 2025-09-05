package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.products.categories.Category;
import com.cdcrane.stockflowbackend.products.enums.ProductLookupTypes;
import com.cdcrane.stockflowbackend.users.ApplicationUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "lookup_type")
    private ProductLookupTypes lookupType;

    @CreatedDate
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private ApplicationUser createdBy;
}
