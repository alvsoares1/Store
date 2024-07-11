package com.example.store.repositories;

import com.example.store.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, String> {
}
