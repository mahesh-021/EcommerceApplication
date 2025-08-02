package com.ecom.EcommerceApplication.Service;

import com.ecom.EcommerceApplication.Model.Product;
import com.ecom.EcommerceApplication.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // Get all products
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // Search products by keyword
    public List<Product> searchProducts(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    // Add new product
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    // Update existing product
    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existing = repository.findById(id);
        if (existing.isPresent()) {
            Product product = existing.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setImageUrl(updatedProduct.getImageUrl());
            return repository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    // Delete product by ID
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}

