package com.ecom.EcommerceApplication.Controller;

import com.ecom.EcommerceApplication.Model.CartItem;
import com.ecom.EcommerceApplication.Model.Order;
import com.ecom.EcommerceApplication.Repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    private final List<CartItem> cartItems; // Simulating cart

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.cartItems = new ArrayList<>();
    }

    // Endpoint to simulate adding item to cart (temporary)
    @PostMapping("/cart")
    public String addToCart(@RequestBody CartItem item) {
        cartItems.add(item);
        return "Item added to cart!";
    }

    // 🚀 Place Order
    @PostMapping("/place")
    public Order placeOrder(@RequestParam Long userId) {
        double total = cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
        Order order = new Order(userId, total, new ArrayList<>(cartItems));
        cartItems.clear();
        return orderRepository.save(order);
    }

    // 📦 Get All Orders for a User
    @GetMapping("/history/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }
}

