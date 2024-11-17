package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Название товара обязательно")
    @Size(max = 255, message = "Название товара не должно превышать 255 символов")
    @Column(nullable = false, length = 255)
    private String name;

    @Size(max = 4096, message = "Описание товара не должно превышать 4096 символов")
    @Column(length = 4096)
    private String description;

    @Min(value = 0, message = "Цена товара не может быть меньше 0")
    @Column(nullable = false, columnDefinition = "double precision default 0.0") // Значение по умолчанию
    private double price = 0;

    @Column(nullable = false, columnDefinition = "boolean default false") // Значение по умолчанию
    private boolean inStock = false;

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
