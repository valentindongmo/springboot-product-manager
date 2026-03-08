package com.productmanager.product_manager.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long idCategory) {
        super("Category with id " + idCategory + " not found");
    }

}