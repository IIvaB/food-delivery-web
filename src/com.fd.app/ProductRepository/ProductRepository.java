package com.fd.app.ProductRepository;

import com.fd.app.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {

    private final Map<String, Product> productMap = new HashMap<>();

    public void addProductMap(Product product) {
        productMap.put(product.getId(), product);
    }

    public Product getProductById(String id) {
        return productMap.get(id);
    }

    public  boolean existsProductById(String id) {
        return productMap.containsKey(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    public void removeProductById(String id) {
        productMap.remove(id);
    }
}