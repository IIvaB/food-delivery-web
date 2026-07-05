package com.fd.app.model;

import com.fd.app.entity.Product;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> cartMap = new HashMap<Product, Integer>();

    public Map<Product, Integer> getCartMap() {
        return cartMap;
    }
}
