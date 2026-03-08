package com.productmanager.product_manager.dto;

public record CategoryResponseDto(
        Long idCategory,
        String name,
        String description,
        String status
) {
}