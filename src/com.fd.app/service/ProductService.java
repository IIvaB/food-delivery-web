package com.fd.app.service;

import com.fd.app.ProductRepository.ProductRepository;
import com.fd.app.model.Product;

import java.util.List;

public class ProductService{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (productRepository.existsProductById(product.getId())) {
            throw new IllegalArgumentException("Product already exists");
        }
        productRepository.addProductMap(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product getProductById(String id){
        if(id == null){
            throw new IllegalArgumentException("Product id cannot be null");
        }
        if(!productRepository.existsProductById(id)){
            throw new IllegalArgumentException("Product not found");
        }
        Product product = productRepository.getProductById(id);
        return product;
    }

    public void removeProductById(String id){
        if(id == null){
            throw new IllegalArgumentException("Product id cannot be null");
        }
        if(!productRepository.existsProductById(id)){
            throw new IllegalArgumentException("Product doesn't exist");
        }
        productRepository.removeProductById(id);
    }
}