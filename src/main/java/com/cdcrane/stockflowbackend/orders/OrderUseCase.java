package com.cdcrane.stockflowbackend.orders;

import com.cdcrane.stockflowbackend.orders.dto.CreateOrderDTO;
import com.cdcrane.stockflowbackend.orders.dto.UpdateOrderDTO;
import com.cdcrane.stockflowbackend.orders.dto.UpdateOrderProductsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderUseCase {

    Page<Order> getOrders(Pageable pageable, String orderBy);

    Order getOrderById(UUID orderId);

    void createOrder(CreateOrderDTO order);

    Order updateOrder(UUID orderId, UpdateOrderDTO order);

    void updateOrderProductInstances(UUID orderId, UpdateOrderProductsDTO productInstanceIds);

    void deleteOrder(UUID orderId);

}
