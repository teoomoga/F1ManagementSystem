package org.example.controller;

import org.example.model.entity.Driver;
import org.example.model.service.DriverService;
import org.example.model.service.EventManager;
import org.example.model.service.NotificationService;
import java.util.List;

public class DriverController {
    private DriverService service = new DriverService();

    public DriverController() {
        EventManager.getInstance().subscribe(new NotificationService());
    }

    public List<Driver> getDriversForTable() {
        return service.getAllDrivers();
    }

    public String addDriver(String name, String team, int points, String nationality, String userEmail) {
        try {
            Driver driver = new Driver(name, team, points, nationality);
            service.addDriver(driver, userEmail);
            return "Succes: Pilotul a fost adăugat!";
        } catch (Exception e) {
            return "Eroare: " + e.getMessage();
        }
    }

    public void handleDeleteDriver(int id, String userEmail) {
        service.deleteDriver(id, userEmail);
    }

    public String handleUpdateDriver(int id, String name, String team, int points, String nationality, String userEmail) {
        try {
            Driver d = new Driver(name, team, points, nationality);
            d.setId(id);
            if (id == 0) {
                service.addDriver(d, userEmail);
            } else {
                service.updateDriver(d, userEmail);
            }
            return "Succes: Operație realizată!";
        } catch (Exception e) {
            return "Eroare la procesare!";
        }
    }

    public List<Driver> handleSearchTeam(String team) {
        return service.searchByTeam(team);
    }

    public List<Driver> handleGetStandings() {
        return service.getStandings();
    }

    public void handleTransfer(int id, String newTeam, String userEmail) {
        service.transferDriver(id, newTeam, userEmail);
    }
}