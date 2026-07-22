package com.fd.app.entity;

import com.fd.app.model.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> items;

    @Column (name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column (name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public Order(List<OrderItem> items) {

        validate(items);

        this.items = new ArrayList<>();

        for (OrderItem item : items) {
            addItem(item);
        }

        this.totalPrice = calculateTotalPrice(this.items);
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.CREATED;

    }

    public Order() {

    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
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

    public void addItem(OrderItem item) {

        items.add(item);

        item.setOrder(this);
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getLineTotal());
        }
        return total;
    }

    private void validate(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items cannot be null or empty");
        }

        // null-елементи + дублікати продуктів
        Set<Long> productIds = new HashSet<>();
        for (OrderItem item : items) {
            if (item == null) throw new IllegalArgumentException("OrderItem cannot be null");

            Long productId = item.getProduct().getId();
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

}
