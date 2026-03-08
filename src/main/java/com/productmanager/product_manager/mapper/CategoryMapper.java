package com.productmanager.product_manager.mapper;

import com.productmanager.product_manager.dto.CategoryRequestDto;
import com.productmanager.product_manager.dto.CategoryResponseDto;
import com.productmanager.product_manager.model.CategoryModel;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public static CategoryModel toEntity(CategoryRequestDto dto) {
        CategoryModel category = new CategoryModel();
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setStatus(dto.status());
        return category;
    }

    public static CategoryResponseDto toResponseDto(CategoryModel category) {
        return new CategoryResponseDto(
                category.getIdCategory(),
                category.getName(),
                category.getDescription(),
                category.getStatus()
        );
    }
}