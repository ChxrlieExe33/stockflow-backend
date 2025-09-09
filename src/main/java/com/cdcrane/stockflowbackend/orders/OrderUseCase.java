package com.cdcrane.stockflowbackend.orders;

import com.cdcrane.stockflowbackend.orders.dto.CreateOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderUseCase {

    Page<Order> getOrders(Pageable pageable, String orderBy);

    void createOrder(CreateOrderDTO order);


}
