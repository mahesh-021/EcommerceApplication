package com.ecom.EcommerceApplication.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "products") // optional but good practice
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    private double price;

    private String imageUrl;

    public Product() {
    }

    public Product(String imageUrl, double price, String description, String name) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.name = name;
    }

    // 🔧 Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
