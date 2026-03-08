package com.productmanager.product_manager.dto;

public record CategoryRequestDto(
        String name,
        String description,
        String status
) {
}