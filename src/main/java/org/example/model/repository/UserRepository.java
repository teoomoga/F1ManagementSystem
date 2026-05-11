package org.example.model.repository;

import org.example.model.entity.User;
import java.sql.*;

public class UserRepository {

    public String getSecurityCodeForTeam(String teamName) {
        String sql = "SELECT security_code FROM teams WHERE team_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teamName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("security_code");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean save(User user) {
        String sql = "INSERT INTO users (username, password, role, managed_team, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getManagedTeam());
            pstmt.setString(5, user.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] authenticate(String username, String password) {
        String sql = "SELECT role, managed_team, email FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new String[]{
                        rs.getString("role"),
                        rs.getString("managed_team"),
                        rs.getString("email")
                };
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void updateSecurityCode(String teamName, String newCode) {
        String sql = "UPDATE teams SET security_code = ? WHERE team_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newCode);
            pstmt.setString(2, teamName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}