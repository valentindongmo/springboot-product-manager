package com.productmanager.product_manager.mapper;

import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.model.CategoryModel;
import com.productmanager.product_manager.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    // Conversion DTO -> Entity
    public ProductModel toEntity(ProductRequestDto dto, CategoryModel category) {
        ProductModel product = new ProductModel();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setCategory(category); // Lier la catégorie
        product.setActive(dto.active());
        return product;
    }

    // Conversion Entity -> DTO
    public ProductResponseDto toResponseDto(ProductModel product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                categoryMapper.toResponseDto(product.getCategory()), // injection utilisée
                product.getActive(),
                product.getCreatedAt()
        );
    }
}