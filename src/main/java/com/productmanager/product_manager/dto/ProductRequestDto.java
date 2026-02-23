package com.productmanager.product_manager.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequestDto(

        @NotBlank(message = "La référence est obligatoire")
        @Size(max = 50, message = "La référence ne doit pas dépasser 50 caractères")
        String reference,

        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String name,

        @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
        String description,

        @NotNull(message = "Le prix est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
        BigDecimal price,

        @DecimalMin(value = "0.0", inclusive = false, message = "Le prix de revient doit être positif")
        BigDecimal costPrice,

        @NotNull(message = "La quantité est obligatoire")
        @Min(value = 0, message = "La quantité ne peut pas être négative")
        Integer quantity,

        @Size(max = 10, message = "La devise ne doit pas dépasser 10 caractères")
        String currency, // XAF, EUR, USD

        @DecimalMin(value = "0.0", message = "La remise ne peut pas être négative")
        @DecimalMax(value = "100.0", message = "La remise ne peut pas dépasser 100%")
        Double discount,

        @Size(max = 30, message = "La marque ne doit pas dépasser 30 caractères")
        String brand,

        Long categoryId
) {}