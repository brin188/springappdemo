package com.example.springappdemo.repository;

import com.example.springappdemo.model.Product;
import com.example.springappdemo.projection.ProductTitleAndDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    //HQL
    @Query("select p.title as title, p.description as description from Product p where p.id = :id")
    ProductTitleAndDescription getProductTitleAndDesc(@Param("id") Long id);


    //SQL
    @Query(value = "select title, description from product where id = :id", nativeQuery = true)
    ProductTitleAndDescription getProductTitleAndDescSQL(@Param("id") Long id);

}
