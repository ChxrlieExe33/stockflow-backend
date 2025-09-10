package com.cdcrane.stockflowbackend.orders;

import com.cdcrane.stockflowbackend.authentication.SecurityUtils;
import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.orders.dto.CreateOrderDTO;
import com.cdcrane.stockflowbackend.orders.dto.UpdateOrderDTO;
import com.cdcrane.stockflowbackend.product_instances.ProductInstanceUseCase;
import com.cdcrane.stockflowbackend.users.ApplicationUser;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepo;
    private final ProductInstanceUseCase productInstanceUseCase;
    private final SecurityUtils securityUtils;

    public OrderService(OrderRepository orderRepo, ProductInstanceUseCase productInstanceUseCase, SecurityUtils securityUtils) {
        this.orderRepo = orderRepo;
        this.productInstanceUseCase = productInstanceUseCase;
        this.securityUtils = securityUtils;
    }

    @Override
    public Page<Order> getOrders(Pageable pageable, String orderBy) {

        Page<Order> orders;

        if (orderBy == null) {
            orderBy = "ordered";
        }

        if (orderBy.equals("delivery")) {

            orders = orderRepo.getUndeliveredOrdersOrderByDeliveryDate(pageable);

        } else if (orderBy.equals("ordered")) {

            orders = orderRepo.getUndeliveredOrdersOrderByOrderedAt(pageable);

        } else {

            orders = orderRepo.getUndeliveredOrdersOrderByOrderedAt(pageable);
        }

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No undelivered orders found");
        }

        return orders;

    }

    @Override
    @Transactional
    public void createOrder(CreateOrderDTO order) {

        ApplicationUser currentUser = securityUtils.getCurrentAuth();

        Order newOrder = Order.builder()
                .reference(order.reference())
                .deliveryDate(order.deliveryDate())
                .deliveryAddress(order.deliveryAddress())
                .deliveryPhoneNumber(order.deliveryPhoneNumber())
                .extraInformation(order.extraInformation())
                .salesPerson(currentUser)
                .build();

        Order saved = orderRepo.save(newOrder);

        productInstanceUseCase.markInstanceAsReserved(order.productInstanceIds(), saved);

    }

    @Override
    @Transactional
    public Order updateOrder(UUID orderId, UpdateOrderDTO dto) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found"));

        order.setReference(dto.reference());
        order.setDeliveryDate(dto.deliveryDate());
        order.setDeliveryAddress(dto.deliveryAddress());
        order.setDeliveryPhoneNumber(dto.deliveryPhoneNumber());
        order.setExtraInformation(dto.extraInformation());
        order.setDelivered(dto.delivered());

        return orderRepo.save(order);

    }

}
