package com.example.store.controllers;

import com.example.store.dto.ProductRequestDTO;
import com.example.store.entities.Product;
import com.example.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
