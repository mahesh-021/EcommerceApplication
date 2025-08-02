package com.ecom.EcommerceApplication.Service;

import com.ecom.EcommerceApplication.Model.User;
import com.ecom.EcommerceApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // 🔹 Register Logic
    public ResponseEntity<?> register(User user) {
        Map<String, Object> response = new HashMap<>();
        if (userRepo.findByEmail(user.getEmail()) != null) {
            response.put("status", "error");
            response.put("message", "Email already registered");
            return ResponseEntity.badRequest().body(response);
        }

        userRepo.save(user);
        response.put("status", "success");
        response.put("message", "Registered successfully");
        return ResponseEntity.ok(response);
    }

    // 🔹 Login Logic
    public ResponseEntity<?> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepo.findByEmail(email);

        if (user == null) {
            response.put("status", "error");
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }

        if (!user.getPassword().equals(password)) {
            response.put("status", "error");
            response.put("message", "Invalid password");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("status", "success");
        response.put("message", "Login successful");
        response.put("user", user); // optional: return user details (not password)
        return ResponseEntity.ok(response);
    }
}
