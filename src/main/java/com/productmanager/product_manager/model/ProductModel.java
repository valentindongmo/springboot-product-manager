package com.productmanager.product_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* ==========================
       CONSTRUCTEURS
       ========================== */

    public ProductModel() {
    }

    public ProductModel(Long id, String name, String description, BigDecimal price, Integer quantity, String category, Boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.active = active;
        this.createdAt = createdAt;
    }

    public ProductModel(Long id, @NotBlank(message = "Le nom du produit est obligatoire") @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères") String name, @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères") String description, @NotNull(message = "Le prix est obligatoire") @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0") BigDecimal price, @NotNull(message = "La quantité est obligatoire") @Min(value = 0, message = "La quantité ne peut pas être négative") Integer quantity, @NotBlank(message = "La catégorie est obligatoire") String category, @NotNull(message = "Le statut est obligatoire") Boolean active) {
    }
   /* ==========================
       PRE-PERSIST
       ========================== */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* ==========================
       GETTERS & SETTERS
       ========================== */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}