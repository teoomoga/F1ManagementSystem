package org.example.model.service;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static EventManager instance;
    private List<DriverEventListener> listeners = new ArrayList<>();

    private EventManager() {}

    public static synchronized EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void subscribe(DriverEventListener listener) {
        listeners.add(listener);
    }

    public void notify(String action, String details, String userEmail) {
        for (DriverEventListener listener : listeners) {
            listener.onDriverChanged(action, details, userEmail);
        }
    }
}