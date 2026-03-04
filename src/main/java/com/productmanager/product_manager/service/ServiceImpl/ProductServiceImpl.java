package com.productmanager.product_manager.service.ServiceImpl;

import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.exception.ProductNotFoundException;
import com.productmanager.product_manager.mapper.ProductMapper;
import com.productmanager.product_manager.model.ProductModel;
import com.productmanager.product_manager.repository.ProductRepository;
import com.productmanager.product_manager.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        ProductModel product = productMapper.toEntity(requestDto); // Conversion DTO → Entity
        ProductModel saved = productRepository.save(product);
        return productMapper.toDto(saved); // Conversion Entity → DTO
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit introuvable avec l'id " + id));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit introuvable avec l'id " + id));

        // Mise à jour de l'Entity avec le DTO
        productMapper.updateEntity(product, requestDto);

        ProductModel updated = productRepository.save(product);
        return productMapper.toDto(updated);
    }

    @Override
    public Boolean deleteProduct(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit introuvable avec l'id " + id));
        productRepository.delete(product);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByStatus(Boolean active) {
        return productRepository.findByActive(active)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto toggleProductStatus(Long id, Boolean active) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit introuvable avec l'id " + id));
        product.setActive(active);
        ProductModel updated = productRepository.save(product);
        return productMapper.toDto(updated);
    }
}