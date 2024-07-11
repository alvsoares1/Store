package com.example.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "wishlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ElementCollection
    private List<WishlistItem> items;

    private float priceTotal;

    private String createdBy;
}

