package com.productmanager.product_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        CategoryResponseDto category, // renvoyer l'objet Category
        Boolean active,
        LocalDateTime createdAt
) {}