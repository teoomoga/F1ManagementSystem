package com.example.identity_service.command;

import com.example.identity_service.model.entity.User;
import lombok.Data;

@Data
public class RegisterUserCommand implements Command<User> {
    private String username;
    private String password;
    private String email;
    private String role;
    private String managedTeam;
}