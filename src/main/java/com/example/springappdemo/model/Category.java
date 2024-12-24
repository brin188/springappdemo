package com.example.springappdemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel implements Serializable {
    String description;

    //@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    //List<Product> products;
}
