package com.productmanager.product_manager.service;


import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.exception.ProductNotFoundException;
import com.productmanager.product_manager.mapper.ProductMapper;
import com.productmanager.product_manager.model.ProductModel;
import com.productmanager.product_manager.repository.ProductRepository;
import com.productmanager.product_manager.service.ServiceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequestDto requestDto;
    private ProductModel productModel;
    private ProductResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new ProductRequestDto(
                1L,
                "Produit Test",
                "Description Test",
                new BigDecimal("99.99"),
                10,
                "Categorie Test",
                true
        );

        productModel = new ProductModel(
                1L,
                "Produit Test",
                "Description Test",
                new BigDecimal("99.99"),
                10,
                "Categorie Test",
                true
        );

        responseDto = new ProductResponseDto(
                1L,
                "Produit Test",
                "Description Test",
                new BigDecimal("99.99"),
                10,
                "Categorie Test",
                true
        );
    }

    @Test
    void testCreateProduct() {
        when(productMapper.toEntity(requestDto)).thenReturn(productModel);
        when(productRepository.save(productModel)).thenReturn(productModel);
        when(productMapper.toDto(productModel)).thenReturn(responseDto);

        ProductResponseDto result = productService.createProduct(requestDto);

        assertNotNull(result);
        assertEquals("Produit Test", result.name());
        verify(productRepository, times(1)).save(productModel);
    }

    @Test
    void testGetProductByIdFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productModel));
        when(productMapper.toDto(productModel)).thenReturn(responseDto);

        ProductResponseDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(productModel));
        when(productMapper.toDto(productModel)).thenReturn(responseDto);

        List<ProductResponseDto> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Produit Test", result.get(0).name());
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productModel));
        doNothing().when(productMapper).updateEntity(productModel, requestDto);
        when(productRepository.save(productModel)).thenReturn(productModel);
        when(productMapper.toDto(productModel)).thenReturn(responseDto);

        ProductResponseDto result = productService.updateProduct(1L, requestDto);

        assertEquals("Produit Test", result.name());
        verify(productMapper, times(1)).updateEntity(productModel, requestDto);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productModel));
        doNothing().when(productRepository).delete(productModel);

        Boolean result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository, times(1)).delete(productModel);
    }
}