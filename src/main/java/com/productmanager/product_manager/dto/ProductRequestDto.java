package com.productmanager.product_manager.dto;


import java.math.BigDecimal;

public record ProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        Long categoryId,   // référence à l'ID de la catégorie
        Boolean active
) {}