package org.example.model.repository;

import org.example.model.entity.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    private Driver extractDriver(ResultSet rs) throws SQLException {
        Driver driver = new Driver(
                rs.getString("name"),
                rs.getString("team"),
                rs.getInt("points"),
                rs.getString("nationality")
        );
        driver.setId(rs.getInt("id"));
        return driver;
    }

    public List<Driver> findAll() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                drivers.add(extractDriver(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public void save(Driver driver) {
        String sql = "INSERT INTO drivers (name, team, points, nationality) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getTeam());
            pstmt.setInt(3, driver.getPoints());
            pstmt.setString(4, driver.getNationality());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM drivers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Driver driver) {
        String sql = "UPDATE drivers SET name = ?, team = ?, points = ?, nationality = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getTeam());
            pstmt.setInt(3, driver.getPoints());
            pstmt.setString(4, driver.getNationality());
            pstmt.setInt(5, driver.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Driver> findByTeam(String teamName) {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers WHERE team LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + teamName + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                drivers.add(extractDriver(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public List<Driver> findAllSortedByPoints() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers ORDER BY points DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                drivers.add(extractDriver(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public void updateDriverTeam(int id, String newTeam) {
        String sql = "UPDATE drivers SET team = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTeam);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}