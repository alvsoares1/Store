package com.example.store.controllers;

import com.example.store.entities.Wishlist;
import com.example.store.entities.WishlistItem;
import com.example.store.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;


    @PostMapping("/create")
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist wishlist) {
        return wishlistService.createWishlist(wishlist);
    }

    @PostMapping("/{wishlistId}/items")
    public ResponseEntity<Wishlist> addItemToWishlist(@PathVariable String wishlistId, @RequestBody WishlistItem newItem) {
        return wishlistService.addItemToWishlist(wishlistId, newItem);
    }
}
