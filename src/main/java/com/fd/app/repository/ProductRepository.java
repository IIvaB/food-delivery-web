package com.fd.app.repository;

import com.fd.app.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {

    private final Map<Long, Product> productMap = new HashMap<>();

    public void addProductMap(Product product) {
        productMap.put(product.getId(), product);
    }

    public Product getProductById(Long id) {
        return productMap.get(id);
    }

    public  boolean existsProductById(Long id) {
        return productMap.containsKey(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    public void removeProductById(Long id) {
        productMap.remove(id);
    }
}