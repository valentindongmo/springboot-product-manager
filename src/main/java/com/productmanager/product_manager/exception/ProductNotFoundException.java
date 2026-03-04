package com.productmanager.product_manager.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Produit non trouvé");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}