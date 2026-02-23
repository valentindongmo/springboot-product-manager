package com.productmanager.product_manager.mapper;

import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.model.ProductModel;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductModel toEntity(ProductRequestDto dto) {
        if (dto == null) return null;

        ProductModel product = new ProductModel();
        product.setReference(dto.reference());
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setCostPrice(dto.costPrice());
        product.setQuantity(dto.quantity());
        product.setCurrency(dto.currency());
        product.setDiscount(dto.discount());
        product.setBrand(dto.brand());
        product.setCategoryId(dto.categoryId());
        return product;
    }

    public static ProductResponseDto toResponse(ProductModel product) {
        if (product == null) return null;

        return new ProductResponseDto(
                product.getIdProduct(),
                product.getReference(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCostPrice(),
                product.getQuantity(),
                product.getCurrency(),
                product.getDiscount(),
                product.getBrand(),
                product.getCategoryId(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}