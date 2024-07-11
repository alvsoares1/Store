package com.example.store.services;

import com.example.store.entities.Product;
import com.example.store.entities.Wishlist;
import com.example.store.entities.WishlistItem;
import com.example.store.infra.TokenService;
import com.example.store.repositories.ProductRepository;
import com.example.store.repositories.WishlistRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<Wishlist> createWishlist(Wishlist wishlist) {
        float priceTotal = 0;


        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token de acesso não encontrado ou inválido");
        }

        String token = authorizationHeader.substring(7);


        String username = tokenService.validateToken(token);

        if (username == null) {
            throw new RuntimeException("Token inválido ou expirado");
        }

        wishlist.setCreatedBy(username);

        for (WishlistItem item : wishlist.getItems()) {
            Optional<Product> optionalProduct = productRepository.findProductByName(item.getProductName());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                item.setPrice(product.getPrice());
                priceTotal += item.getPrice() * item.getQuantity();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }

        wishlist.setPriceTotal(priceTotal);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok(savedWishlist);
    }

    public ResponseEntity<Wishlist> addItemToWishlist(String wishlistId, WishlistItem newItem) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);

        if (optionalWishlist.isPresent()) {
            Wishlist wishlist = optionalWishlist.get();
            Optional<Product> optionalProduct = productRepository.findProductByName(newItem.getProductName());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                newItem.setPrice(product.getPrice());
                wishlist.getItems().add(newItem);
                wishlist.setPriceTotal(wishlist.getPriceTotal() + newItem.getPrice() * newItem.getQuantity());
                Wishlist updatedWishlist = wishlistRepository.save(wishlist);
                return ResponseEntity.ok(updatedWishlist);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
