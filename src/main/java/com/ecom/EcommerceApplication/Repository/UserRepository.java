package com.ecom.EcommerceApplication.Repository;


import com.ecom.EcommerceApplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // for login
}

