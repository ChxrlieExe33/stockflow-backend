package com.cdcrane.stockflowbackend.orders;

import com.cdcrane.stockflowbackend.orders.dto.CreateOrderDTO;
import com.cdcrane.stockflowbackend.orders.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getOrders(@RequestParam(required = false) String orderBy, Pageable pageable) {

        Page<Order> orders = orderUseCase.getOrders(pageable ,orderBy);

        var response = orders.map(o -> new OrderDTO(o.getId(), o.getReference(), o.getDeliveryDate(),
                o.getDeliveryAddress(), o.getDeliveryPhoneNumber(), o.getExtraInformation(),
                o.getOrderedAt(), o.getSalesPerson() != null ? o.getSalesPerson().getUsername() : "Unknown"));

        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDTO order) {

        orderUseCase.createOrder(order);

        return ResponseEntity.ok().build();

    }
}
