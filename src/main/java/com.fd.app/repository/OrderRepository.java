package com.fd.app.repository;

import com.fd.app.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {
    private final Map<Long, Order> storage = new HashMap<>();

    public Order save(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");

        Long id = order.getId();

        storage.put(id, order);
        return order;
    }

    public Order findById(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Order id cannot be null or blank");
        return storage.get(id);
    }

    public boolean existsById(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Order id cannot be null or blank");
        return storage.containsKey(id);
    }

    public List<Order> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Order id cannot be null or blank");
        if (!storage.containsKey(id)) throw new IllegalArgumentException("Order not found");
        storage.remove(id);
    }
}
