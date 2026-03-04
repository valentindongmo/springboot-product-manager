package com.productmanager.product_manager.service;


import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {


    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);


    List<ProductResponseDto> getAllProducts();

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);


    Boolean deleteProduct(Long id);


    List<ProductResponseDto> getProductsByCategory(String category);

    List<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);


    List<ProductResponseDto> getProductsByStatus(Boolean active);


    ProductResponseDto toggleProductStatus(Long id, Boolean active);

}
