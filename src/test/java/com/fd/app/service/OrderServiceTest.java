package com.fd.app.service;

import com.fd.app.model.*;
import com.fd.app.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private CartService cartService;
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
        orderRepository = new OrderRepository();
        orderService = new OrderService(cartService, orderRepository);
    }

    @Test
    void createOrder_createsOrder_andClearsCart() {
        Cart cart = new Cart();

        Product burger = new Product("p1", "Burger", new BigDecimal("120.00"));
        Product cola = new Product("p2", "Cola", new BigDecimal("30.00"));

        cartService.addProduct(cart, burger, 2);
        cartService.addProduct(cart, cola, 1);

        Order order = orderService.createOrder(cart);

        assertNotNull(order.getId());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(new BigDecimal("270.00"), order.getTotalPrice());

        assertTrue(cart.getCartMap().isEmpty());
        assertTrue(orderRepository.existsById(order.getId()));
    }

    @Test
    void markAsPaid_changesStatusToPaid() {
        Order order = createSimpleOrder();

        orderService.markAsPaid(order.getId());

        assertEquals(
                OrderStatus.PAID,
                orderService.getById(order.getId()).getStatus()
        );
    }

    @Test
    void markAsDelivered_afterPaid_changesStatusToDelivered() {
        Order order = createSimpleOrder();

        orderService.markAsPaid(order.getId());
        orderService.markAsDelivered(order.getId());

        assertEquals(
                OrderStatus.DELIVERED,
                orderService.getById(order.getId()).getStatus()
        );
    }

    @Test
    void cancel_fromCreated_changesStatusToCancelled() {
        Order order = createSimpleOrder();

        orderService.cancel(order.getId());

        assertEquals(
                OrderStatus.CANCELLED,
                orderService.getById(order.getId()).getStatus()
        );
    }

    @Test
    void cancel_afterPaid_throwsException() {
        Order order = createSimpleOrder();

        orderService.markAsPaid(order.getId());

        assertThrows(
                IllegalStateException.class,
                () -> orderService.cancel(order.getId())
        );
    }

    private Order createSimpleOrder() {
        Cart cart = new Cart();
        Product cola = new Product("p1", "Cola", new BigDecimal("30.00"));
        cartService.addProduct(cart, cola, 1);
        return orderService.createOrder(cart);
    }
}