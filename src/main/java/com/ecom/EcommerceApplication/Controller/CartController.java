package com.ecom.EcommerceApplication.Controller;

import com.ecom.EcommerceApplication.Model.CartItem;
import com.ecom.EcommerceApplication.Model.Product;
import com.ecom.EcommerceApplication.Model.User;
import com.ecom.EcommerceApplication.Repository.CartRepository;
import com.ecom.EcommerceApplication.Repository.ProductRepository;
import com.ecom.EcommerceApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // ✅ Add to cart
    @PostMapping("/add")
    public String addToCart(@RequestParam String email,
                            @RequestParam Long productId,
                            @RequestParam int quantity) {
        User user = userRepository.findByEmail(email);
        if (user == null) return "User not found";

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return "Product not found";

        CartItem existing = cartRepository.findByUserAndProductId(user, productId);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartRepository.save(existing);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartRepository.save(newItem);
        }

        return "Added to cart";
    }

    // ✅ View cart items
    @GetMapping("/items")
    public List<CartItem> viewCart(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return null;
        return cartRepository.findByUser(user);
    }

    // ✅ Remove item from cart
    @DeleteMapping("/remove")
    public String removeItem(@RequestParam String email, @RequestParam Long productId) {
        User user = userRepository.findByEmail(email);
        if (user == null) return "User not found";

        CartItem item = cartRepository.findByUserAndProductId(user, productId);
        if (item == null) return "Item not found in cart";

        cartRepository.delete(item);
        return "Removed from cart";
    }

    // ✅ Clear entire cart (optional)
    @DeleteMapping("/clear")
    public String clearCart(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return "User not found";

        cartRepository.deleteByUser(user);
        return "Cart cleared";
    }
}


