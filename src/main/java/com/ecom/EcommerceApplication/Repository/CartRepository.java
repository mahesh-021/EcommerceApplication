package com.ecom.EcommerceApplication.Repository;

import com.ecom.EcommerceApplication.Model.CartItem;
import com.ecom.EcommerceApplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user); // 🛒 Get all cart items for a user

    void deleteByUser(User user); // 🧹 Clear entire cart for a user

    CartItem findByUserAndProductId(User user, Long productId); // 🔍 Find specific item in cart
}
