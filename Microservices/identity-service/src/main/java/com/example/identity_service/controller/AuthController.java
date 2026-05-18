package com.example.identity_service.controller;

import com.example.identity_service.command.RegisterUserCommand;
import com.example.identity_service.command.RegisterUserCommandHandler;
import com.example.identity_service.model.entity.User;
import com.example.identity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/commands")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final RegisterUserCommandHandler registerHandler;
    private final UserRepository repository;

    private void sendNotification(String message) {
        try {
            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            java.util.Map<String, String> body = new java.util.HashMap<>();
            body.put("message", message);
            body.put("serviceSource", "Identity Service");
            restTemplate.postForEntity("http://localhost:8083/api/notifications", body, String.class);
        } catch (Exception e) {
            System.err.println("Notification Service error: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserCommand cmd) {
        User u = registerHandler.handle(cmd);
        sendNotification("New user registered: " + u.getUsername() + " as " + u.getRole());
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            sendNotification("User logged in: " + username);
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(401).body("Invalid credentials!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            sendNotification("User with ID " + id + " was deleted.");
            return ResponseEntity.ok("User deleted successfully!");
        }
        return ResponseEntity.status(404).body("User not found!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<User> userOpt = repository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (body.containsKey("role")) user.setRole(body.get("role"));
            if (body.containsKey("managedTeam")) user.setManagedTeam(body.get("managedTeam"));
            repository.save(user);
            sendNotification("User " + user.getUsername() + " updated (Role: " + user.getRole() + ", Team: " + user.getManagedTeam() + ")");
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(404).body("User not found!");
    }
}