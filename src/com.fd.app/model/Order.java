package com.fd.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Order {
    private final String id;
    private final List<OrderItem> items;
    private final BigDecimal totalPrice;
    private final LocalDateTime createdAt;
    private OrderStatus status;

    public Order(String id, List<OrderItem> items) {

        validate(id, items);

        this.id = id;
        this.items = List.copyOf(items);          // захист від змін ззовні
        this.totalPrice = calculateTotalPrice(this.items);
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items; // List.copyOf зробив її immutable, тож повертати безпечно
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getTotalQuantity() {
        int total = 0;
        for (OrderItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getLineTotal());
        }
        return total;
    }

    private void validate(String id, List<OrderItem> items) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Order id cannot be null or blank");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items cannot be null or empty");
        }

        // null-елементи + дублікати продуктів
        Set<String> productIds = new HashSet<>();
        for (OrderItem item : items) {
            if (item == null) throw new IllegalArgumentException("OrderItem cannot be null");

            String productId = item.getProduct().getId();
            if (!productIds.add(productId)) {
                throw new IllegalArgumentException("Duplicate product in order: " + productId);
            }
        }
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
