package com.productmanager.product_manager.repository;


import com.productmanager.product_manager.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    // Recherche les produits dont la catégorie a ce nom
    List<ProductModel> findByCategory_Name(String categoryName);

    // Récupère tous les produits dont le prix est compris entre minPrice et maxPrice
    List<ProductModel> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Récupère tous les produits selon leur statut actif/inactif
    List<ProductModel> findByActive(Boolean active);

}