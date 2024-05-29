package com.example.store.controllers;

import com.example.store.dto.ProductRequestDTO;
import com.example.store.entities.Product;
import com.example.store.entities.ProductCategory;
import com.example.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestBody ProductRequestDTO body){
        Optional<Product> product = this.productRepository.findProductByName(body.name());

        if(product.isEmpty()){
            Product newProduct = new Product();
            newProduct.setName(body.name());
            newProduct.setDescription(body.description());
            newProduct.setPrice(body.price());
            newProduct.setCategory(body.category());
            newProduct.setQuantity(body.quantity());
            this.productRepository.save(newProduct);
            return ResponseEntity.ok().body(newProduct);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity findByCategory(@PathVariable ProductCategory category){
        List<Product> products = this.productRepository.findProductByCategory(category);
        if(products.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(products);
    }
}
