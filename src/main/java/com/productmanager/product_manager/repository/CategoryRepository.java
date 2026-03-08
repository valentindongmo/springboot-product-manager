package com.productmanager.product_manager.repository;
import com.productmanager.product_manager.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    List<CategoryModel> findByNameContainingIgnoreCase(String name);
    @Query("SELECT c FROM CategoryModel c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CategoryModel> searchByName(@Param("name") String name);
}