package com.example.springappdemo.service;

import com.example.springappdemo.model.Product;
import com.example.springappdemo.repository.ProductRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    ProductRepo productRepo;

    public SearchService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> searchProduct(String keyword, int pageNumber, int pageSize) {
        return productRepo.findByTitleContains(keyword, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "price")));
    }
}
