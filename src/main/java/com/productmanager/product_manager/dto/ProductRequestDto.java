package com.productmanager.product_manager.dto;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDto(

        Long id,

        @NotBlank(message = "Le nom du produit est obligatoire")
        @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
        String name,

        @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
        String description,

        @NotNull(message = "Le prix est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
        BigDecimal price,

        @NotNull(message = "La quantité est obligatoire")
        @Min(value = 0, message = "La quantité ne peut pas être négative")
        Integer quantity,

        @NotBlank(message = "La catégorie est obligatoire")
        String category,

        @NotNull(message = "Le statut est obligatoire")
        Boolean active
) {
}