package org.example.model.entity;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String managedTeam;
    private String email;

    public User(String username, String password, String role, String managedTeam, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.managedTeam = managedTeam;
        this.email = email;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getManagedTeam() { return managedTeam; }
    public String getEmail() { return email; }
}