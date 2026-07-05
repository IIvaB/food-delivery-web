package com.fd.app.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be >= 0");
        }

        this.name = name;
        this.price = price;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

//    @Override
//    public boolean equals(Object o) {
//        System.out.println("equals done");
//        if (this == o) return true;
//        if (!(o instanceof Product)) return false;
//        Product product = (Product) o;
//        return Objects.equals(id, product.id);
//    }
//
//    @Override
//    public int hashCode() {
//        System.out.println("hash done");
//        return Objects.hash(id);
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", price=" + price +
//                '}';
//    }
}
