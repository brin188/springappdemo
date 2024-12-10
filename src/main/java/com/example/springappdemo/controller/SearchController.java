package com.example.springappdemo.controller;

import com.example.springappdemo.model.Product;
import com.example.springappdemo.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("?keyword={keyword}")
    public List<Product> search(@RequestParam("keyword") String keyword,
                                @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return searchService.searchProduct(keyword, pageNumber, pageSize);
    }
}
