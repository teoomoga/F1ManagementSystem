package org.example.model.service;

public class NotificationService implements DriverEventListener {
    @Override
    public void onDriverChanged(String action, String details, String userEmail) {
        String message = "NOTIFICARE EMAIL către " + userEmail + ":\n" +
                "Acțiunea: " + action + "\n" +
                "Detalii: " + details;

        System.out.println("--------------------------------------------");
        System.out.println(message);
        System.out.println("--------------------------------------------");
    }
}