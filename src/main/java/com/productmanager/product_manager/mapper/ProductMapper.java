package com.productmanager.product_manager.mapper;

import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    /**
     * Convertit ProductRequestDto → ProductModel
     */
    public ProductModel toEntity(ProductRequestDto requestDto) {
        return new ProductModel(
                requestDto.id(),
                requestDto.name(),
                requestDto.description(),
                requestDto.price(),
                requestDto.quantity(),
                requestDto.category(),
                requestDto.active()
        );
    }

    /**
     * Convertit ProductModel → ProductResponseDto
     */
    public ProductResponseDto toDto(ProductModel product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getActive()
                // ajouté pour correspondre au record ProductResponseDto
        );
    }

    /**
     * Met à jour un ProductModel existant avec les données du DTO
     */
    public void updateEntity(ProductModel product, ProductRequestDto requestDto) {
        product.setName(requestDto.name());
        product.setDescription(requestDto.description());
        product.setPrice(requestDto.price());
        product.setQuantity(requestDto.quantity());
        product.setCategory(requestDto.category());
        product.setActive(requestDto.active());
    }
}