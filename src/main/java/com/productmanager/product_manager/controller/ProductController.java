package com.productmanager.product_manager.controller;


import com.productmanager.product_manager.dto.ProductRequestDto;
import com.productmanager.product_manager.dto.ProductResponseDto;
import com.productmanager.product_manager.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // =========================
    // CREATE
    // =========================
    @PostMapping("/save")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        ProductResponseDto created = productService.createProduct(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // =========================
    // READ - Get by ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // =========================
    // READ - Get all products
    // =========================
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // =========================
    // UPDATE
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto updated = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // FILTER - By category
    // =========================
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponseDto> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    // =========================
    // FILTER - By price range
    // =========================
    @GetMapping("/price")
    public ResponseEntity<List<ProductResponseDto>> getProductsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<ProductResponseDto> products = productService.getProductsByPriceRange(min, max);
        return ResponseEntity.ok(products);
    }

    // =========================
    // FILTER - By active status
    // =========================
    @GetMapping("/status")
    public ResponseEntity<List<ProductResponseDto>> getProductsByStatus(@RequestParam Boolean active) {
        List<ProductResponseDto> products = productService.getProductsByStatus(active);
        return ResponseEntity.ok(products);
    }

    // =========================
    // TOGGLE STATUS
    // =========================
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductResponseDto> toggleProductStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        ProductResponseDto updated = productService.toggleProductStatus(id, active);
        return ResponseEntity.ok(updated);
    }
}