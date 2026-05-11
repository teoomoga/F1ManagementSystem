package com.example.driver_service.command;

import com.example.driver_service.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteDriverCommandHandler implements CommandHandler<DeleteDriverCommand, Void> {

    @Autowired
    private DriverRepository repository;

    @Override
    public Void handle(DeleteDriverCommand command) {
        if (!repository.existsById(command.getId())) {
            throw new RuntimeException("Pilotul cu ID-ul " + command.getId() + " nu există!");
        }
        repository.deleteById(command.getId());
        return null;
    }
}