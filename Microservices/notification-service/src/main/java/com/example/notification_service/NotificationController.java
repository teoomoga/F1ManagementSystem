package com.example.notification_service;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
    private final NotificationRepository repository;

    public NotificationController(NotificationRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        notification.setTimestamp(java.time.LocalDateTime.now());
        return repository.save(notification);
    }

    @GetMapping
    public List<Notification> getAll() {
        return repository.findAllByOrderByTimestampDesc();
    }
}
