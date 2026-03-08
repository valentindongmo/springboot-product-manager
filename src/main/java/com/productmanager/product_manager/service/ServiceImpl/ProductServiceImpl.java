
package com.productmanager.product_manager.service.ServiceImpl;

import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.exception.ProductNotFoundException;
import com.productmanager.product_manager.mapper.ProductMapper;
import com.productmanager.product_manager.model.CategoryModel;
import com.productmanager.product_manager.model.ProductModel;
import com.productmanager.product_manager.repository.CategoryRepository;
import com.productmanager.product_manager.repository.ProductRepository;
import com.productmanager.product_manager.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        // Récupérer la catégorie depuis la base
        CategoryModel category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Catégorie introuvable avec l'id " + requestDto.categoryId()));

        ProductModel product = productMapper.toEntity(requestDto, category);
        ProductModel saved = productRepository.save(product);
        return productMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit introuvable avec l'id " + id));
        return productMapper.toResponseDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit introuvable avec l'id " + id));

        CategoryModel category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Catégorie introuvable avec l'id " + requestDto.categoryId()));

        // Mettre à jour l'entity
        product.setName(requestDto.name());
        product.setDescription(requestDto.description());
        product.setPrice(requestDto.price());
        product.setQuantity(requestDto.quantity());
        product.setCategory(category);
        product.setActive(requestDto.active());

        ProductModel updated = productRepository.save(product);
        return productMapper.toResponseDto(updated);
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
    public List<ProductResponseDto> getProductsByCategory(String categoryName) {
        return productRepository.findByCategory_Name(categoryName)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByStatus(Boolean active) {
        return productRepository.findByActive(active)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto toggleProductStatus(Long id, Boolean active) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit introuvable avec l'id " + id));
        product.setActive(active);
        ProductModel updated = productRepository.save(product);
        return productMapper.toResponseDto(updated);
    }
}