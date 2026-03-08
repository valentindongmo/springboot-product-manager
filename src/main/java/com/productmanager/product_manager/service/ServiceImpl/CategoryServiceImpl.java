package com.productmanager.product_manager.service.ServiceImpl;

import com.productmanager.product_manager.dto.CategoryRequestDto;
import com.productmanager.product_manager.dto.CategoryResponseDto;
import com.productmanager.product_manager.exception.CategoryNotFoundException;
import com.productmanager.product_manager.mapper.CategoryMapper;
import com.productmanager.product_manager.model.CategoryModel;
import com.productmanager.product_manager.repository.CategoryRepository;
import com.productmanager.product_manager.service.CategoryService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        CategoryModel category = CategoryMapper.toEntity(categoryRequestDto);
        CategoryModel savedCategory = categoryRepository.save(category);
        return CategoryMapper.toResponseDto(savedCategory);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long idCategory) {
        CategoryModel category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new CategoryNotFoundException(idCategory));
        return CategoryMapper.toResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto updateCategory(Long idCategory, CategoryRequestDto categoryRequestDto) {
        CategoryModel category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new CategoryNotFoundException(idCategory));
        category.setName(categoryRequestDto.name());
        category.setDescription(categoryRequestDto.description());
        category.setStatus(categoryRequestDto.status());
        CategoryModel updatedCategory = categoryRepository.save(category);
        return CategoryMapper.toResponseDto(updatedCategory);
    }

    @Override
    public Boolean deleteCategory(Long idCategory) {
        CategoryModel category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new CategoryNotFoundException(idCategory));
        categoryRepository.delete(category);
        return true;
    }

    @Override
    public List<CategoryResponseDto> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(CategoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
