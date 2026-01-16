package com.fd.app.service;

import com.fd.app.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductService{

    private Map<String, Product> productMap = new HashMap<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        productMap.put(product.getId(), product);
    }

    public void showAllProducts(){
        for(Product product:productMap.values()){
            System.out.println(product);
        }
    }
}