package com.example.store.dto;

import com.example.store.entities.ProductCategory;

public record ProductRequestDTO(String name, String description, int price, int quantity, ProductCategory category) {}
