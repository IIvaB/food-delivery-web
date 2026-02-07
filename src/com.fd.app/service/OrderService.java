package com.fd.app.service;

import com.fd.app.model.Cart;
import com.fd.app.model.Order;
import com.fd.app.model.OrderItem;
import com.fd.app.model.OrderStatus;
import com.fd.app.model.Product;
import com.fd.app.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;

    public OrderService(CartService cartService, OrderRepository orderRepository) {
        if (cartService == null) throw new IllegalArgumentException("CartService cannot be null");
        if (orderRepository == null) throw new IllegalArgumentException("OrderRepository cannot be null");
        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Cart cart) {
        validateCart(cart);

        List<OrderItem> items = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cart.getCartMap().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            validateCartEntry(product, quantity);

            items.add(new OrderItem(product, quantity, product.getPrice()));
        }

        Order order = new Order(UUID.randomUUID().toString(), items);

        orderRepository.save(order);     // якщо save впаде — кошик не очиститься
        cartService.clearCart(cart);

        return order;
    }

    public Order getById(String orderId) {
        validateOrderId(orderId);

        Order order = orderRepository.findById(orderId);
        if (order == null) throw new IllegalArgumentException("Order not found");

        return order;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public void markAsPaid(String orderId) {
        changeStatus(orderId, OrderStatus.PAID);
    }

    public void markAsDelivered(String orderId) {
        changeStatus(orderId, OrderStatus.DELIVERED);
    }

    public void cancel(String orderId) {
        changeStatus(orderId, OrderStatus.CANCELLED);
    }

    private void changeStatus(String orderId, OrderStatus targetStatus) {
        if (targetStatus == null) throw new IllegalArgumentException("Target status cannot be null");

        Order order = getById(orderId);
        OrderStatus currentStatus = order.getStatus();

        if (!currentStatus.canTransitionTo(targetStatus)) {
            throw new IllegalStateException("Cannot change status from " + currentStatus + " to " + targetStatus);
        }

        order.setStatus(targetStatus);
        orderRepository.save(order);
    }

    private void validateOrderId(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order id cannot be null or blank");
        }
    }

    private void validateCart(Cart cart) {
        if (cart == null) throw new IllegalArgumentException("Cart cannot be null");
        if (cart.getCartMap() == null || cart.getCartMap().isEmpty()) {
            throw new IllegalArgumentException("Cart cannot be empty");
        }
    }

    private void validateCartEntry(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");

        BigDecimal price = product.getPrice();
        if (price == null) throw new IllegalArgumentException("Product price cannot be null");
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Product price cannot be negative");
    }
}
