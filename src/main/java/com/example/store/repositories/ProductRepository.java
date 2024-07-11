package com.example.store.repositories;

import com.example.store.entities.Product;
import com.example.store.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, String> {
    Optional<Product> findProductByName(String name);
    List<Product> findProductByCategory(ProductCategory category);
}
