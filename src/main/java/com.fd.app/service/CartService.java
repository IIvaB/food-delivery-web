package com.fd.app.service;

import com.fd.app.model.Cart;
import com.fd.app.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public class CartService {

    public void addProduct(Cart cart, Product product, int quantity) {
        validate(cart, product, quantity);

        Map<Product, Integer> cartMap = cart.getCartMap();
        cartMap.merge(product, quantity, Integer::sum);
    }

    public void decreaseProduct(Cart cart, Product product, int quantity) {
        validate(cart, product, quantity);

        Map<Product, Integer> cartMap = cart.getCartMap();
        Integer current = cartMap.get(product);

        if (current == null) throw  new IllegalArgumentException("Product not found");
        if (quantity > current) throw  new IllegalArgumentException("Quantity higher than product's quantity");

        int updated = current - quantity;
        if (updated > 0) {
            cartMap.put(product, updated);
        } else {
            cartMap.remove(product);
        }
    }

    public void removeProduct(Cart cart, Product product) {
        if (cart == null) throw new IllegalArgumentException("Cart cannot be null");
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if(!cart.getCartMap().containsKey(product)) throw  new IllegalArgumentException("Product not found");

        cart.getCartMap().remove(product);
    }

    public void clearCart(Cart cart) {
        if (cart == null) throw new IllegalArgumentException("Cart cannot be null");
        cart.getCartMap().clear();
    }

    private void validate(Cart cart, Product product, int quantity) {
        if (cart == null) throw new IllegalArgumentException("Cart cannot be null");
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");
    }

    public BigDecimal getTotalPrice(Cart cart) {
        if (cart == null) throw new IllegalArgumentException("Cart cannot be null");

        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : cart.getCartMap().entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            total = total.add(lineTotal);
        }
        return total;
    }

    public int getTotalQuantity(Cart cart) {
        if (cart == null) throw new IllegalArgumentException("Cart cannot be null");

        int total = 0;
        for (Integer quantity : cart.getCartMap().values()) {
            total += quantity;
        }
        return total;
    }
}