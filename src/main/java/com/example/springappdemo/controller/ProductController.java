package com.example.springappdemo.controller;

import com.example.springappdemo.exception.ProductNotFoundException;
import com.example.springappdemo.model.Product;
import com.example.springappdemo.service.ProductService;
import com.example.springappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;
    UserService userService;

    public ProductController(@Qualifier("SelfProductService") ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) // , @RequestHeader("token") String token
            throws ProductNotFoundException {
//        if(!userService.validateToken(token))
//            throw new AuthenticationException("Invalid token. Access denied.");
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") long id, @RequestBody Product product)
            throws ProductNotFoundException {
        Product replacedProduct = productService.replaceProduct(id, product);
        return new ResponseEntity<>(replacedProduct, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product)
            throws ProductNotFoundException {
        Product updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id)
            throws ProductNotFoundException {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Deleted product with id " + id, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.OK);
    }
}
