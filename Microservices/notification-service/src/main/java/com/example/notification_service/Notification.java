package com.example.notification_service;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String serviceSource;
    private LocalDateTime timestamp;

    public Notification(String message, String serviceSource) {
        this.message = message;
        this.serviceSource = serviceSource;
        this.timestamp = LocalDateTime.now();
    }
}
