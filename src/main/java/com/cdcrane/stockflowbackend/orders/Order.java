package com.cdcrane.stockflowbackend.orders;

import com.cdcrane.stockflowbackend.product_instances.ProductInstance;
import com.cdcrane.stockflowbackend.users.ApplicationUser;
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
    @Column(updatable = false)
    private Instant orderedAt;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_id")
    private List<ProductInstance> products;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private ApplicationUser salesPerson;
}
