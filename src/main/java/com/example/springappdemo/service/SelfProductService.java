package com.example.springappdemo.service;

import com.example.springappdemo.exception.ProductNotFoundException;
import com.example.springappdemo.model.Category;
import com.example.springappdemo.model.Product;
import com.example.springappdemo.repository.CategoryRepo;
import com.example.springappdemo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("SelfProductService")
@Primary
public class SelfProductService implements ProductService {

    ProductRepo productRepo;
    CategoryRepo categoryRepo;
    RestTemplate restTemplate;

    public SelfProductService(ProductRepo productRepo, CategoryRepo categoryRepo, RestTemplate restTemplate) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(100L, "Product not found for id " + id);
        }
        return product.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product replaceProduct(long id, Product product) throws ProductNotFoundException {
        Product oldProduct = getProductById(id);
        oldProduct.setTitle(product.getTitle());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        Category category = categoryRepo.findById(product.getCategory().getId()).get();
        oldProduct.setCategory(category);
        return productRepo.save(oldProduct);
    }

    @Override
    public void deleteProduct(long id) throws ProductNotFoundException {
        Product product = getProductById(id);
        productRepo.delete(product);
    }

    @Override
    public Product updateProduct(long id, Product product) throws ProductNotFoundException {
        Product productInDB = getProductById(id);
        if(product.getTitle() != null)
            productInDB.setTitle(product.getTitle());
        if(product.getDescription() != null)
            productInDB.setDescription(product.getDescription());
        if(product.getPrice() != null)
            productInDB.setPrice(product.getPrice());
        if(product.getCategory() != null) {
            Category category = categoryRepo.findById(product.getCategory().getId()).get();
            productInDB.setCategory(category);
        }
        return productRepo.save(productInDB);
    }

    @Override
    public Product addProduct(Product product) {
          Category category = product.getCategory();
          if(category != null && category.getId() != null) {
              category = categoryRepo.findById(category.getId()).get();
              product.setCategory(category);
          }
        return productRepo.save(product);
    }
}
