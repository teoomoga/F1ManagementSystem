package org.example.model.service;

import org.example.model.entity.Driver;
import org.example.model.repository.DriverRepository;
import java.util.List;

public class DriverService {
    private DriverRepository repository = new DriverRepository();

    private void notifyListeners(String action, String details, String userEmail) {
        EventManager.getInstance().notify(action, details, userEmail);
    }

    public List<Driver> getAllDrivers() {
        return repository.findAll();
    }

    public void addDriver(Driver driver, String userEmail) {
        if (driver.getName() == null || driver.getName().trim().isEmpty()) return;
        if (driver.getPoints() < 0) return;

        repository.save(driver);
        notifyListeners("CREATE", "Pilotul " + driver.getName() + " a fost adăugat în sistem.", userEmail);
    }

    public void deleteDriver(int id, String userEmail) {
        repository.delete(id);
        notifyListeners("DELETE", "Pilotul cu ID-ul " + id + " a fost șters.", userEmail);
    }

    public void updateDriver(Driver driver, String userEmail) {
        if (driver.getPoints() < 0) return;
        repository.update(driver);
        notifyListeners("UPDATE", "Datele pilotului " + driver.getName() + " au fost actualizate.", userEmail);
    }

    public void transferDriver(int id, String newTeam, String userEmail) {
        repository.updateDriverTeam(id, newTeam);
        notifyListeners("TRANSFER", "Pilotul cu ID " + id + " a fost transferat la echipa " + newTeam, userEmail);
    }

    public List<Driver> getDriversByRole(String role, String managedTeam) {
        if (role.equals("ADMIN") || role.equals("VISITOR")) {
            return repository.findAll();
        } else {
            return repository.findAll().stream()
                    .filter(d -> d.getTeam().equalsIgnoreCase(managedTeam))
                    .toList();
        }
    }

    public List<Driver> searchByTeam(String team) {
        return repository.findByTeam(team);
    }

    public List<Driver> getStandings() {
        return repository.findAllSortedByPoints();
    }
}