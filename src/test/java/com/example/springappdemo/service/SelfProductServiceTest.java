package com.example.springappdemo.service;

import com.example.springappdemo.exception.ProductNotFoundException;
import com.example.springappdemo.model.Category;
import com.example.springappdemo.model.Product;
import com.example.springappdemo.repository.CategoryRepo;
import com.example.springappdemo.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class SelfProductServiceTest {
    @Autowired
    SelfProductService selfProductService;

    @MockBean
    ProductRepo productRepo;

    @MockBean
    CategoryRepo categoryRepo;

    @Test
    void getProductById() throws ProductNotFoundException {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setTitle("Test Product 1");
        Optional<Product> productOptional = Optional.of(product);

        when(productRepo.findById(productId)).thenReturn(productOptional);

        // Act
        Product p = selfProductService.getProductById(productId);

        // Assert
        assertEquals("Test Product 1", p.getTitle());
    }

    @Test
    void getProductByIdThrowsException() throws ProductNotFoundException {
        // Arrange
        long productId = 5L;

        // Mock rule
        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class,
                () -> selfProductService.getProductById(productId));
    }

    @Test
    void getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());

        when(productRepo.findAll()).thenReturn(products);
        List<Product> results = selfProductService.getAllProducts();
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

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepo.findById(2L)).thenReturn(Optional.of(category));
        when(productRepo.save(product)).thenReturn(product);
        Product actualProduct = selfProductService.replaceProduct(product.getId(),product);
        assertNotNull(actualProduct);
        assertEquals("Test Product 1", actualProduct.getTitle());
    }

    @Test
    void deleteProduct() throws ProductNotFoundException {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepo).delete(product);
        selfProductService.deleteProduct(productId);
        assertTrue(true);
    }

    @Test
    void updateProduct() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product 1");
        Category category = new Category();
        category.setId(2L);
        category.setTitle("Test Category 1");
        category.setDescription("Test Category Description");
        product.setCategory(category);

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepo.findById(2L)).thenReturn(Optional.of(category));
        when(productRepo.save(product)).thenReturn(product);
        Product actualProduct = selfProductService.updateProduct(product.getId(),product);
        assertNotNull(actualProduct);
        assertEquals("Test Product 1", actualProduct.getTitle());
    }

    @Test
    void addProduct() {
        Product product = new Product();
        product.setTitle("Test Product 1");
        Category category = new Category();
        category.setId(2L);
        category.setTitle("Test Category 1");
        category.setDescription("Test Category Description");
        product.setCategory(category);

        when(categoryRepo.findById(2L)).thenReturn(Optional.of(category));
        when(productRepo.save(product)).thenReturn(product);
        Product actualProduct = selfProductService.addProduct(product);
        assertNotNull(actualProduct);
        assertEquals("Test Product 1", actualProduct.getTitle());
    }
}