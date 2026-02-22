package com.productmanager.product_manager.service;


import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    public ProductResponseDto createProduct(ProductRequestDto request);

    public ProductResponseDto getProductById(Long id);

    public List<ProductResponseDto> getAllProducts();

    public Page<ProductResponseDto> getAllProducts(Pageable pageable);

    public Page<ProductResponseDto> searchProductsByName(String name, Pageable pageable);

    public List<ProductResponseDto> getProductsByCategory(Long categoryId);

    Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable);

    public List<ProductResponseDto> getProductsByPriceRange(Double minPrice, Double maxPrice);

    Page<ProductResponseDto> getProductsByPriceRange(Double minPrice, Double maxPrice, Pageable pageable);

    public ProductResponseDto updateProduct(Long id, ProductRequestDto request);


    public Boolean deleteProduct(Long id);

    public long countProducts(); // nombre total de produits

}
