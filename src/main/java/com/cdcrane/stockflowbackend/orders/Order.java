package com.cdcrane.stockflowbackend.orders;

import com.cdcrane.stockflowbackend.product_instances.ProductInstance;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    private String reference;

    private Date deliveryDate;

    private String deliveryAddress;

    private String deliveryPhoneNumber;

    private String extraInformation;

    private boolean delivered;

    @CreatedDate
    private Instant orderedAt;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<ProductInstance> products;
}
