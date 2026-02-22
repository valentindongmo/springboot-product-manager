package com.productmanager.product_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDto(
        Long idProduct,
        String reference,
        String name,
        String description,
        BigDecimal price,
        BigDecimal costPrice,
        Integer quantity,
        String currency,
        Double discount,
        String brand,
        Long categoryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}