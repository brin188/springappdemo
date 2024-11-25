package com.example.springappdemo.controller;

import com.example.springappdemo.exception.ProductNotFoundException;
import com.example.springappdemo.model.Category;
import com.example.springappdemo.model.Product;
import com.example.springappdemo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
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
        long productId = 5L;

        // Mock rule
        when(productService.getProductById(productId)).thenThrow(ProductNotFoundException.class);

        // Act & Assert
        assertThrows(ProductNotFoundException.class,
                () -> productController.getProductById(productId).getBody());
    }

    @Test
    void getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());

        when(productService.getAllProducts()).thenReturn(products);
        List<Product> results = productController.getAllProducts().getBody();
        assertNotNull(results);
        assertEquals(products.size(), results.size());
    }

    @Test
    void replaceProduct() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product 1");
        product.setDescription("Test Description");
        product.setPrice(99.99);
        Category category = new Category();
        category.setId(2L);
        category.setTitle("Test Category 1");
        category.setDescription("Test Category Description");
        product.setCategory(category);

        when(productService.replaceProduct(product.getId(),product)).thenReturn(product);
        Product actualProduct = productController.replaceProduct(product.getId(),product).getBody();
        assertNotNull(actualProduct);
        assertEquals(product.getTitle(), actualProduct.getTitle());
    }

    @Test
    void updateProduct() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        product.setDescription("Test Description");

        when(productService.updateProduct(product.getId(),product)).thenReturn(product);
        Product actualProduct = productController.updateProduct(product.getId(),product).getBody();
        assertNotNull(actualProduct);
        assertEquals(product.getDescription(), actualProduct.getDescription());
    }

    @Test
    void deleteProduct() throws ProductNotFoundException {
        long productId = 1L;

        doNothing().when(productService).deleteProduct(productId);
        String result = productController.deleteProduct(productId).getBody();
        assertEquals("Deleted product with id " + productId, result);
    }

    @Test
    void addProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product 1");
        product.setDescription("Test Description");
        product.setPrice(99.99);
        Category category = new Category();
        category.setId(2L);
        category.setTitle("Test Category 1");
        category.setDescription("Test Category Description");
        product.setCategory(category);

        when(productService.addProduct(product)).thenReturn(product);
        Product actualProduct = productController.addProduct(product).getBody();
        assertNotNull(actualProduct);
        assertEquals(product.getId(), actualProduct.getId());
    }
}