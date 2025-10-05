package com.cdcrane.stockflowbackend.orders;

import com.cdcrane.stockflowbackend.orders.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID orderId) {

        Order order = orderUseCase.getOrderById(orderId);

        return ResponseEntity.ok(mapOrderToOrderDto(order));

    }

    @GetMapping("/products/{orderId}")
    public ResponseEntity<List<OrderProductInstanceProjection>> getOrderProductInstances(@PathVariable UUID orderId) {

        var result = orderUseCase.getOrderProductInstances(orderId);

        return ResponseEntity.ok(result);

    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDTO order) {

        orderUseCase.createOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderInformation(@PathVariable UUID orderId, @RequestBody UpdateOrderDTO order) {

        Order result = orderUseCase.updateOrder(orderId, order);

        return ResponseEntity.ok(mapOrderToOrderDto(result));

    }

    /**
     * To add and remove product instances from a sale.
     * @param orderId The order to update.
     * @param instances The DTO containing the IDS of the products.
     * @return A void response.
     */
    @PutMapping("/instances/{orderId}")
    public ResponseEntity<Void> removeItemsFromOrder(@PathVariable UUID orderId, @RequestBody UpdateOrderProductsDTO instances) {

        orderUseCase.updateOrderProductInstances(orderId, instances);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

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
