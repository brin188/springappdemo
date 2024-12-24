package com.example.springappdemo.service;

import com.example.springappdemo.dto.FakeStoreProductDto;
import com.example.springappdemo.exception.ProductNotFoundException;
import com.example.springappdemo.model.Category;
import com.example.springappdemo.model.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class FakeStoreProductService implements ProductService {

    RestTemplate restTemplate;
    RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + id);
        if (product != null) {
            System.out.println("Product found in Redis");
            return product;
        }
        FakeStoreProductDto dto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);
        // Creating FakeStoreProductDto for testing redis because the api service is down
//        FakeStoreProductDto dto = new FakeStoreProductDto();
//        dto.setId(id);
//        dto.setCategory("1");
//        dto.setPrice(10.0);
//        dto.setDescription("This is a fake store product");
//        dto.setTitle("This is a fake store product");

        if(dto == null)
            throw new ProductNotFoundException(100L, "Product not found for id " + id);
        product = convertFakeStoreProductDtoToProduct(dto);
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + id, product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] dtos = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto dto : dtos) {
            products.add(convertFakeStoreProductDtoToProduct(dto));
        }
        return products;
    }

    @Override
    public Product replaceProduct(long id, Product product) throws ProductNotFoundException {
        FakeStoreProductDto requestDto = new FakeStoreProductDto();
        requestDto.setTitle(product.getTitle());
        requestDto.setDescription(product.getDescription());
        requestDto.setPrice(product.getPrice());
        if(product.getCategory() != null) {
            requestDto.setCategory(product.getCategory().getTitle());
        }

        RequestCallback requestCallback = restTemplate.httpEntityCallback(requestDto, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        FakeStoreProductDto responseDto = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT,
                requestCallback, responseExtractor).getBody();
        if(responseDto == null)
            throw new ProductNotFoundException(100L, "Product not found for id " + id);
        return convertFakeStoreProductDtoToProduct(responseDto);
    }

    @Override
    public void deleteProduct(long id) throws ProductNotFoundException {
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        FakeStoreProductDto responseDto = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.DELETE,
                null, responseExtractor).getBody();
        if(responseDto == null)
            throw new ProductNotFoundException(100L, "Product not found for id " + id);
    }

    @Override
    public Product updateProduct(long id, Product product) throws ProductNotFoundException {
        FakeStoreProductDto requestDto = new FakeStoreProductDto();
        requestDto.setTitle(product.getTitle());
        requestDto.setDescription(product.getDescription());
        requestDto.setPrice(product.getPrice());
        if(product.getCategory() != null) {
            requestDto.setCategory(product.getCategory().getTitle());
        }
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        FakeStoreProductDto responseDto = restTemplate.patchForObject(
                "https://fakestoreapi.com/products/" + id, requestDto, FakeStoreProductDto.class);

        if(responseDto == null)
            throw new ProductNotFoundException(100L, "Product not found for id " + id);

        return convertFakeStoreProductDtoToProduct(responseDto);
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProductDto requestDto = new FakeStoreProductDto();
        requestDto.setTitle(product.getTitle());
        requestDto.setDescription(product.getDescription());
        requestDto.setPrice(product.getPrice());
        if(product.getCategory() != null) {
            requestDto.setCategory(product.getCategory().getTitle());
        }

        FakeStoreProductDto responseDto = restTemplate.postForObject("https://fakestoreapi.com/products", requestDto, FakeStoreProductDto.class);
        return convertFakeStoreProductDtoToProduct(responseDto);
    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto dto) {
        if(dto == null) {
            return null;
        }

        // Create a new Product instance
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        Category category = new Category();
        category.setTitle(dto.getCategory());  // Assuming the category name is passed as a string
        product.setCategory(category);  // Set the created Category object

        return product;
    }
}
