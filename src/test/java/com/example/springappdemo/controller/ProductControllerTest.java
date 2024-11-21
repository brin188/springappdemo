package com.example.springappdemo.controller;

import com.example.springappdemo.exception.ProductNotFoundException;
import com.example.springappdemo.model.Product;
import com.example.springappdemo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    ProductController productController;

    @Test
    void getProductById() throws ProductNotFoundException {
        // Arrange
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setTitle("Test Product 1");

        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        Product p = productController.getProductById(productId).getBody();

        // Assert
        assertEquals("Test Product 1", p.getTitle());
    }

    @Test
    void getProductByIdThrowsException() throws ProductNotFoundException {
        // Arrange
        long productId = 1L;

        // Mock rule
        when(productService.getProductById(productId)).thenThrow(ProductNotFoundException.class);

        // Act & Assert
        assertThrows(ProductNotFoundException.class,
                () -> productController.getProductById(productId).getBody());
    }
}