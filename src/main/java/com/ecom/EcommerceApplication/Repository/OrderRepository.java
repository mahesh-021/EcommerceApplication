package com.ecom.EcommerceApplication.Repository;

import com.ecom.EcommerceApplication.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);  // To fetch past orders for a user
}

