package com.fd.app.service;

import com.fd.app.model.Cart;
import com.fd.app.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    @Test
    void addProduct_newProduct_addsProductToCart() {
        // Arrange
        CartService service = new CartService();
        Cart cart = new Cart();
        Product cola = new Product("p1", "Cola", new BigDecimal("30.00"));

        // Act
        service.addProduct(cart, cola, 2);

        // Assert
        assertEquals(2, service.getTotalQuantity(cart));
        assertEquals(new BigDecimal("60.00"), service.getTotalPrice(cart));
    }

    @Test
    void clearCart_removesAllProducts() {
        CartService service = new CartService();
        Cart cart = new Cart();
        Product cola = new Product("p1", "Cola", new BigDecimal("30.00"));

        service.addProduct(cart, cola, 2);
        service.clearCart(cart);

        assertEquals(0, service.getTotalQuantity(cart));
        assertEquals(new BigDecimal("0"), service.getTotalPrice(cart));
    }

    @Test
    void removeProduct_removeProductFromCart() {
        CartService service = new CartService();
        Cart cart = new Cart();

        Product cola = new Product("p1", "Cola", new BigDecimal("30.00"));
        Product apple = new Product("p2", "Apple", new BigDecimal("40.00"));

        service.addProduct(cart, cola, 2);
        service.addProduct(cart, apple, 2);

        service.removeProduct(cart, cola);

        assertEquals(2, service.getTotalQuantity(cart));
        assertEquals(new BigDecimal("80.00"), service.getTotalPrice(cart));
    }

    @Test
    void addProduct_sameProductTwice_increasesQuantity() {
        CartService service = new CartService();
        Cart cart = new Cart();

        Product cola = new Product("p1", "Cola", new BigDecimal("30.00"));

        service.addProduct(cart, cola, 2);
        service.addProduct(cart, cola, 3);

        assertEquals(5, service.getTotalQuantity(cart));
        assertEquals(new BigDecimal("150.00"), service.getTotalPrice(cart));
    }

    @Test
    void decreaseProduct_toZero_removesProductFromCart() {
        CartService service = new CartService();
        Cart cart = new Cart();

        Product cola = new Product("p1", "Cola", new BigDecimal("30.00"));

        service.addProduct(cart, cola, 2);

        service.decreaseProduct(cart, cola, 2);

        assertEquals(0, service.getTotalQuantity(cart));
        assertEquals(new BigDecimal("0"), service.getTotalPrice(cart));
        assertFalse(cart.getCartMap().containsKey(cola));
    }

    @Test
    void decreaseProduct_moreThanInCart_throwsException() {
        CartService service = new CartService();
        Cart cart = new Cart();

        Product cola = new Product("p1", "Cola", new BigDecimal("30.00"));
        service.addProduct(cart, cola, 2);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.decreaseProduct(cart, cola, 3)
        );
    }
}