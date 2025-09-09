package com.cdcrane.stockflowbackend.orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.delivered = false ORDER BY o.deliveryDate DESC")
    Page<Order> getUndeliveredOrdersOrderByDeliveryDate(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.delivered = false ORDER BY o.orderedAt DESC")
    Page<Order> getUndeliveredOrdersOrderByOrderedAt(Pageable pageable);



}
