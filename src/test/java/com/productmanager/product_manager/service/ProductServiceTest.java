package com.productmanager.product_manager.service;



import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.exception.ProductNotFoundException;
import com.productmanager.product_manager.model.ProductModel;
import com.productmanager.product_manager.repository.ProductRepository;
import com.productmanager.product_manager.service.ServiceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductModel product;
    private ProductRequestDto productRequest;

    @BeforeEach
    void setUp() {
        product = new ProductModel(
                1L,
                "REF001",
                "Produit Test",
                "Description test",
                new BigDecimal("100.0"),
                new BigDecimal("80.0"),
                10,
                "USD",
                5.0,
                "BrandTest",
                1L,
                null,
                null,
                "admin",
                null
        );

        productRequest = new ProductRequestDto(
                "REF001",
                "Produit Test",
                "Description test",
                new BigDecimal("100.0"),
                new BigDecimal("80.0"),
                10,
                "USD",
                5.0,
                "BrandTest",
                1L
        );
    }

    // ===================== CREATE =====================
    @Test
    void testCreateProduct() {
        when(productRepository.save(any(ProductModel.class))).thenReturn(product);

        ProductResponseDto response = productService.createProduct(productRequest);

        assertNotNull(response);
        assertEquals(product.getReference(), response.reference());
        assertEquals(product.getName(), response.name());
        verify(productRepository, times(1)).save(any(ProductModel.class));
    }

    // ===================== GET BY ID =====================
    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDto response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals(product.getReference(), response.reference());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    // ===================== GET ALL =====================
    @Test
    void testGetAllProducts() {
        List<ProductModel> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponseDto> response = productService.getAllProducts();

        assertEquals(1, response.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetAllProducts_Pageable() {
        Page<ProductModel> page = new PageImpl<>(List.of(product));
        Pageable pageable = PageRequest.of(0, 10);
        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<ProductResponseDto> response = productService.getAllProducts(pageable);

        assertEquals(1, response.getTotalElements());
        verify(productRepository, times(1)).findAll(pageable);
    }

    // ===================== SEARCH BY NAME =====================
    @Test
    void testSearchProductsByName() {
        Page<ProductModel> page = new PageImpl<>(List.of(product));
        Pageable pageable = PageRequest.of(0, 10);
        when(productRepository.findByNameContainingIgnoreCase("Produit", pageable)).thenReturn(page);

        Page<ProductResponseDto> response = productService.searchProductsByName("Produit", pageable);

        assertEquals(1, response.getTotalElements());
        verify(productRepository, times(1)).findByNameContainingIgnoreCase("Produit", pageable);
    }

    // ===================== GET BY CATEGORY =====================
    @Test
    void testGetProductsByCategory_List() {
        when(productRepository.findByCategoryId(1L)).thenReturn(List.of(product));

        List<ProductResponseDto> response = productService.getProductsByCategory(1L);

        assertEquals(1, response.size());
        verify(productRepository, times(1)).findByCategoryId(1L);
    }

    @Test
    void testGetProductsByCategory_Pageable() {
        Page<ProductModel> page = new PageImpl<>(List.of(product));
        Pageable pageable = PageRequest.of(0, 10);
        when(productRepository.findByCategoryId(1L, pageable)).thenReturn(page);

        Page<ProductResponseDto> response = productService.getProductsByCategory(1L, pageable);

        assertEquals(1, response.getTotalElements());
        verify(productRepository, times(1)).findByCategoryId(1L, pageable);
    }

    // ===================== GET BY PRICE RANGE =====================
    @Test
    void testGetProductsByPriceRange_List() {
        when(productRepository.findByPriceBetween(50.0, 150.0)).thenReturn(List.of(product));

        List<ProductResponseDto> response = productService.getProductsByPriceRange(50.0, 150.0);

        assertEquals(1, response.size());
        verify(productRepository, times(1)).findByPriceBetween(50.0, 150.0);
    }

    @Test
    void testGetProductsByPriceRange_Pageable() {
        Page<ProductModel> page = new PageImpl<>(List.of(product));
        Pageable pageable = PageRequest.of(0, 10);
        when(productRepository.findByPriceBetween(50.0, 150.0, pageable)).thenReturn(page);

        Page<ProductResponseDto> response = productService.getProductsByPriceRange(50.0, 150.0, pageable);

        assertEquals(1, response.getTotalElements());
        verify(productRepository, times(1)).findByPriceBetween(50.0, 150.0, pageable);
    }

    // ===================== UPDATE =====================
    @Test
    void testUpdateProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductModel.class))).thenReturn(product);

        ProductResponseDto response = productService.updateProduct(1L, productRequest);

        assertNotNull(response);
        assertEquals(productRequest.name(), response.name());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, productRequest));
        verify(productRepository, times(1)).findById(1L);
    }

    // ===================== DELETE =====================
    @Test
    void testDeleteProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Boolean deleted = productService.deleteProduct(1L);

        assertTrue(deleted);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    // ===================== COUNT =====================
    @Test
    void testCountProducts() {
        when(productRepository.count()).thenReturn(5L);

        long count = productService.countProducts();

        assertEquals(5L, count);
        verify(productRepository, times(1)).count();
    }
}

