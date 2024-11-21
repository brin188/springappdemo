package com.example.springappdemo.service;

import com.example.springappdemo.exception.ProductNotFoundException;
import com.example.springappdemo.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product replaceProduct(long id, Product product) throws ProductNotFoundException;

    Product deleteProduct(long id) throws ProductNotFoundException;

    Product updateProduct(long id, Product product) throws ProductNotFoundException;

    Product addProduct(Product product);
}
