package com.example.springappdemo.service;

import com.example.springappdemo.exception.ProductNotFoundException;
import com.example.springappdemo.model.Category;
import com.example.springappdemo.model.Product;
import com.example.springappdemo.repository.CategoryRepo;
import com.example.springappdemo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SelfProductService")
@Primary
public class SelfProductService implements ProductService {

    ProductRepo productRepo;

    CategoryRepo categoryRepo;

    public SelfProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        return productRepo.findById(id).get();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product replaceProduct(long id, Product product) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product deleteProduct(long id) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product updateProduct(long id, Product product) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        Category category = product.getCategory();
        System.out.println("Category id - " + category.getId());
        if(category.getId() == null) {
            Category newCategory = categoryRepo.save(category);
            product.setCategory(newCategory);
        }
        return productRepo.save(product);
    }
}
