package com.example.store.repositories;

import com.example.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, String> {
    Optional<Product> findProductByName(String name);
    Optional<Product> findProductByPrice(int price);
    Optional<Product> findProductByCategory(String category);
}
