package com.productmanager.product_manager.service.ServiceImpl;


import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.exception.ProductNotFoundException;
import com.productmanager.product_manager.mapper.ProductMapper;
import com.productmanager.product_manager.model.ProductModel;
import com.productmanager.product_manager.repository.ProductRepository;
import com.productmanager.product_manager.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final  ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto request) {
        ProductModel product = ProductMapper.toEntity(request);
        ProductModel saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }


    @Override
    public ProductResponseDto getProductById(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toResponse(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }


    @Override
    public Page<ProductResponseDto> searchProductsByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    public List<ProductResponseDto> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponseDto> getProductsByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.findByPriceBetween(minPrice, maxPrice, pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto request) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setReference(request.reference());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCostPrice(request.costPrice());
        product.setQuantity(request.quantity());
        product.setCurrency(request.currency());
        product.setDiscount(request.discount());
        product.setBrand(request.brand());
        product.setCategoryId(request.categoryId());

        ProductModel updated = productRepository.save(product);
        return ProductMapper.toResponse(updated);
    }

    @Override
    public Boolean deleteProduct(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
        return true;
    }

    @Override
    public long countProducts() {
        return productRepository.count();
    }

}
