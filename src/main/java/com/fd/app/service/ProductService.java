package com.fd.app.service;

import com.fd.app.repository.ProductRepository;
import com.fd.app.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        if(id == null){
            throw new IllegalArgumentException("Product id cannot be null");
        }

        Optional<Product> product = productRepository.findById(id);

        if(product == null){
            throw new IllegalArgumentException("Product not found");
        }

        return product;
    }

    public void deleteProductById(Long id){
        if(id == null){
            throw new IllegalArgumentException("Product id cannot be null");
        }
        productRepository.deleteById(id);
    }
}