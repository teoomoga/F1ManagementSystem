package com.example.identity_service.command;

import com.example.identity_service.model.entity.User;
import com.example.identity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserCommandHandler {
    private final UserRepository repository;

    public User handle(RegisterUserCommand cmd) {
        User user = new User();
        user.setUsername(cmd.getUsername());
        user.setPassword(cmd.getPassword());
        user.setEmail(cmd.getEmail());

        if (cmd.getRole() == null || cmd.getRole().isEmpty()) {
            user.setRole("GUEST");
        } else {
            user.setRole(cmd.getRole().toUpperCase());
        }
        user.setManagedTeam(cmd.getManagedTeam());

        return repository.save(user);
    }
}