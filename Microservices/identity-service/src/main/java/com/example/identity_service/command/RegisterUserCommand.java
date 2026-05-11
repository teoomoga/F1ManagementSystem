package com.example.identity_service.command;

import lombok.Data;

@Data
public class RegisterUserCommand {
    private String username;
    private String password;
    private String email;
    private String role;
    private String managedTeam;
}