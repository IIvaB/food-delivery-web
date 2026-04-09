package com.fd.app;

import com.fd.app.entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args){

        Session session = com.fd.app.util.HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

// просто створюємо будь-що
        Product product = new Product("Test", new BigDecimal("10.00"));

        session.persist(product);

        tx.commit();
        session.close();
//        Product product = new Product("1", "Apple", BigDecimal.valueOf(12.4));
//        Product product2 = new Product("1", "Apple", BigDecimal.valueOf(12.4));
//        Product product3 = new Product("2", "Orange", BigDecimal.valueOf(14));
//
//        productService.addProduct(product);
//        productService.addProduct(product2);
//        productService.addProduct(product3);
//        productService.getProductById("4");
//        productService.showAllProducts();
    }
}

