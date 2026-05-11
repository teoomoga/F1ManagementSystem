package org.example.model.service;

public interface DriverEventListener {
    void onDriverChanged(String action, String details, String userEmail);
}