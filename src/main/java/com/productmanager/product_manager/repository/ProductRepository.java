package com.productmanager.product_manager.repository;


import com.productmanager.product_manager.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    Page<ProductModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<ProductModel> findByCategoryId(Long categoryId);
    Page<ProductModel> findByCategoryId(Long categoryId, Pageable pageable);

    List<ProductModel> findByPriceBetween(Double minPrice, Double maxPrice);
    Page<ProductModel> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    @Query("SELECT COUNT(p) FROM ProductModel p WHERE FUNCTION('DATE', p.createdAt) = CURRENT_DATE")
    long countProductsByDay();

    @Query("SELECT COUNT(p) FROM ProductModel p WHERE FUNCTION('MONTH', p.createdAt) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', p.createdAt) = FUNCTION('YEAR', CURRENT_DATE)")
    long countProductsByMonth();

    @Query("SELECT COUNT(p) FROM ProductModel p WHERE FUNCTION('YEAR', p.createdAt) = FUNCTION('YEAR', CURRENT_DATE)")
    long countProductsByYear();


    boolean existsByReference(String reference);

}
