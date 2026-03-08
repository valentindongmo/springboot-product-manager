package com.productmanager.product_manager.service;

import com.productmanager.product_manager.dto.CategoryRequestDto;
import com.productmanager.product_manager.dto.CategoryResponseDto;

import java.util.List;


public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);


    CategoryResponseDto getCategoryById(Long idCategory);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto updateCategory(Long idCategory, CategoryRequestDto categoryRequestDto);

    Boolean deleteCategory(Long idCategory);

    List<CategoryResponseDto> findByName(String name);
}