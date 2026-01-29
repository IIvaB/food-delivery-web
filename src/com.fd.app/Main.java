package com.fd.app;

import com.fd.app.model.Product;
import com.fd.app.service.ProductService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args){

        Product product = new Product("1", "Apple", BigDecimal.valueOf(12.4));
        Product product2 = new Product("1", "Apple", BigDecimal.valueOf(12.4));
        Product product3 = new Product("2", "Orange", BigDecimal.valueOf(14));

        productService.addProduct(product);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.getProductById("4");
        productService.showAllProducts();
    }
}