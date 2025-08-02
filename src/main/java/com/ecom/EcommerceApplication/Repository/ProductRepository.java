package com.ecom.EcommerceApplication.Repository;

import com.ecom.EcommerceApplication.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 🔍 Search products by name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String keyword);
}

