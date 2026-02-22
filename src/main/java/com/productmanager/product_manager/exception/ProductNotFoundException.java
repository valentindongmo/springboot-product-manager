package com.productmanager.product_manager.exception;

import com.productmanager.product_manager.model.ProductModel;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long idProduct) {
        super("Produit non trouvé avec l'id : " + idProduct);
    }

    public ProductNotFoundException(ProductModel product) {
        super("Produit non trouvé : " + product);
    }
}