package com.productmanager.product_manager.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        String category,
        Boolean active
) {
}