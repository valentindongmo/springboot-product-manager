package com.productmanager.product_manager.dto;

import java.math.BigDecimal;

public record ProductRequestDto(
        String reference,
        String name,
        String description,
        BigDecimal price,
        BigDecimal costPrice,
        Integer quantity,
        String currency,
        Double discount,
        String brand,
        Long categoryId
) {
}