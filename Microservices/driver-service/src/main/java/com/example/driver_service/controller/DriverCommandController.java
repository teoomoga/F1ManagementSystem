package com.example.driver_service.controller;

import com.example.driver_service.command.*;
import com.example.driver_service.model.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/drivers/commands")
public class DriverCommandController {

    @Autowired
    private CreateDriverCommandHandler createHandler;

    @Autowired
    private DeleteDriverCommandHandler deleteHandler;

    @Autowired
    private UpdateDriverCommandHandler updateHandler;

    private void sendNotification(String message) {
        try {
            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            java.util.Map<String, String> body = new java.util.HashMap<>();
            body.put("message", message);
            body.put("serviceSource", "Driver Service");
            restTemplate.postForEntity("http://localhost:8083/api/notifications", body, String.class);
        } catch (Exception e) {
            System.err.println("Notification Service error: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public Driver createDriver(@RequestBody CreateDriverCommand command) {
        Driver d = createHandler.handle(command);
        sendNotification("New driver created: " + d.getName() + " for team " + d.getTeam());
        return d;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDriver(@PathVariable Long id) {
        DeleteDriverCommand cmd = new DeleteDriverCommand(id);
        deleteHandler.handle(cmd);
        sendNotification("Driver with ID " + id + " was deleted.");
        return "Driver deleted successfully!";
    }

    @PutMapping("/update/{id}")
    public Driver updateDriver(@PathVariable Long id, @RequestBody UpdateDriverCommand command) {
        command.setId(id);
        Driver d = updateHandler.handle(command);
        sendNotification("Driver updated: " + d.getName() + " (Points: " + d.getPoints() + ")");
        return d;
    }
}