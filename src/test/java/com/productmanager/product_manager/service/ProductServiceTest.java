package com.productmanager.product_manager.service;

import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.dto.CategoryResponseDto;
import com.productmanager.product_manager.exception.ProductNotFoundException;
import com.productmanager.product_manager.mapper.ProductMapper;
import com.productmanager.product_manager.mapper.CategoryMapper;
import com.productmanager.product_manager.model.CategoryModel;
import com.productmanager.product_manager.model.ProductModel;
import com.productmanager.product_manager.repository.ProductRepository;
import com.productmanager.product_manager.repository.CategoryRepository;
import com.productmanager.product_manager.service.ServiceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - ProductService")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequestDto requestDto;
    private ProductModel productModel;
    private ProductResponseDto responseDto;
    private CategoryModel category;
    private CategoryResponseDto categoryResponseDto;
    private LocalDateTime now; // ✅ fixé pour éviter les problèmes de timing

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now(); // ✅ une seule instance partagée

        category = new CategoryModel(1L, "Categorie Test", "Description Cat", "Active");

        categoryResponseDto = new CategoryResponseDto(
                category.getIdCategory(),
                category.getName(),
                category.getDescription(),
                category.getStatus()
        );

        requestDto = new ProductRequestDto(
                "Produit Test",
                "Description Test",
                new BigDecimal("99.99"),
                10,
                category.getIdCategory(),
                true
        );

        productModel = new ProductModel();
        productModel.setId(1L);
        productModel.setName(requestDto.name());
        productModel.setDescription(requestDto.description());
        productModel.setPrice(requestDto.price());
        productModel.setQuantity(requestDto.quantity());
        productModel.setCategory(category);
        productModel.setActive(requestDto.active());
        productModel.setCreatedAt(now);

        // ✅ responseDto construit avec 1L en dur pour garantir l'ID
        responseDto = new ProductResponseDto(
                1L,
                productModel.getName(),
                productModel.getDescription(),
                productModel.getPrice(),
                productModel.getQuantity(),
                categoryResponseDto,
                productModel.getActive(),
                now
        );
    }

    @Test
    @DisplayName("Doit créer un produit avec succès")
    void testCreateProduct() {
        when(categoryRepository.findById(category.getIdCategory())).thenReturn(Optional.of(category));
        when(productMapper.toEntity(requestDto, category)).thenReturn(productModel);
        when(productRepository.save(productModel)).thenReturn(productModel);
        when(productMapper.toResponseDto(productModel)).thenReturn(responseDto);

        ProductResponseDto result = productService.createProduct(requestDto);

        assertNotNull(result);
        assertEquals("Produit Test", result.name());
        assertEquals(category.getName(), result.category().name());
        verify(productRepository, times(1)).save(productModel);
    }

    @Test
    @DisplayName("Doit retourner un produit par ID existant")
    void testGetProductByIdFound() {
        // ✅ responseDto avec 1L garanti dans setUp()
        when(productRepository.findById(1L)).thenReturn(Optional.of(productModel));
        when(productMapper.toResponseDto(productModel)).thenReturn(responseDto);

        ProductResponseDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id()); // ✅ passe maintenant
        assertEquals(category.getName(), result.category().name());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Doit lever ProductNotFoundException si produit introuvable")
    void testGetProductByIdNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(99L));

        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Doit retourner la liste de tous les produits")
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(productModel));
        when(productMapper.toResponseDto(productModel)).thenReturn(responseDto);

        List<ProductResponseDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Produit Test", result.get(0).name());
    }

    @Test
    @DisplayName("Doit retourner une liste vide si aucun produit")
    void testGetAllProductsEmpty() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProductResponseDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Doit mettre à jour un produit avec succès")
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productModel));
        when(categoryRepository.findById(category.getIdCategory())).thenReturn(Optional.of(category));
        when(productRepository.save(productModel)).thenReturn(productModel);
        when(productMapper.toResponseDto(productModel)).thenReturn(responseDto);

        ProductResponseDto result = productService.updateProduct(1L, requestDto);

        assertNotNull(result);
        assertEquals("Produit Test", result.name());
        assertEquals(category.getName(), result.category().name());
        verify(productRepository, times(1)).save(productModel);
    }

    @Test
    @DisplayName("Doit supprimer un produit avec succès")
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productModel));
        doNothing().when(productRepository).delete(productModel);

        Boolean result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository, times(1)).delete(productModel);
    }

    @Test
    @DisplayName("Doit retourner les produits par catégorie")
    void testGetProductsByCategory() {
        when(productRepository.findByCategory_Name(category.getName()))
                .thenReturn(List.of(productModel));
        when(productMapper.toResponseDto(productModel)).thenReturn(responseDto);

        List<ProductResponseDto> result = productService.getProductsByCategory(category.getName());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(category.getName(), result.get(0).category().name());
    }

    @Test
    @DisplayName("Doit retourner les produits dans une fourchette de prix")
    void testGetProductsByPriceRange() {
        BigDecimal min = new BigDecimal("50");
        BigDecimal max = new BigDecimal("150");

        when(productRepository.findByPriceBetween(min, max)).thenReturn(List.of(productModel));
        when(productMapper.toResponseDto(productModel)).thenReturn(responseDto);

        List<ProductResponseDto> result = productService.getProductsByPriceRange(min, max);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("99.99"), result.get(0).price());
    }

    @Test
    @DisplayName("Doit retourner les produits par statut actif")
    void testGetProductsByStatus() {
        when(productRepository.findByActive(true)).thenReturn(List.of(productModel));
        when(productMapper.toResponseDto(productModel)).thenReturn(responseDto);

        List<ProductResponseDto> result = productService.getProductsByStatus(true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).active());
    }

    @Test
    @DisplayName("Doit basculer le statut d'un produit")
    void testToggleProductStatus() {
        productModel.setActive(true);

        ProductResponseDto toggledResponse = new ProductResponseDto(
                1L,
                productModel.getName(),
                productModel.getDescription(),
                productModel.getPrice(),
                productModel.getQuantity(),
                categoryResponseDto,
                false, // ✅ statut basculé
                now
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(productModel));
        when(productRepository.save(productModel)).thenReturn(productModel);
        when(productMapper.toResponseDto(productModel)).thenReturn(toggledResponse);

        ProductResponseDto result = productService.toggleProductStatus(1L, false);

        assertNotNull(result);
        assertEquals("Produit Test", result.name());
        assertFalse(result.active());
        verify(productRepository, times(1)).save(productModel);
    }
}