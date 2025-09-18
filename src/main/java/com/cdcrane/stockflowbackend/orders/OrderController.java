package com.cdcrane.stockflowbackend.orders;

import com.cdcrane.stockflowbackend.orders.dto.CreateOrderDTO;
import com.cdcrane.stockflowbackend.orders.dto.OrderDTO;
import com.cdcrane.stockflowbackend.orders.dto.UpdateOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getOrders(@RequestParam(required = false) String orderBy, @PageableDefault(size = 10) Pageable pageable) {

        Page<Order> orders = orderUseCase.getOrders(pageable ,orderBy);

        var response = orders.map(this::mapOrderToOrderDto);

        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDTO order) {

        orderUseCase.createOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable UUID orderId, @RequestBody UpdateOrderDTO order) {

        Order result = orderUseCase.updateOrder(orderId, order);

        return ResponseEntity.ok(mapOrderToOrderDto(result));

    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {

        orderUseCase.deleteOrder(orderId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }



    // -------------------------------- HELPER METHODS --------------------------------

    private OrderDTO mapOrderToOrderDto(Order order) {

        return new OrderDTO(order.getId(), order.getReference(), order.getDeliveryDate(),
                order.getDeliveryAddress(), order.getDeliveryPhoneNumber(), order.getExtraInformation(),
                order.getOrderedAt(), order.getSalesPerson() != null ? order.getSalesPerson().getUsername() : "Unknown", order.isDelivered());
    }
}
