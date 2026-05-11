package com.example.driver_service.command;

import com.example.driver_service.model.entity.Driver;
import com.example.driver_service.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateDriverCommandHandler implements CommandHandler<UpdateDriverCommand, Driver> {

    @Autowired
    private DriverRepository repository;

    @Override
    public Driver handle(UpdateDriverCommand command) {
        Driver existingDriver = repository.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Pilotul cu ID-ul " + command.getId() + " nu a fost găsit!"));

        existingDriver.setName(command.getName());
        existingDriver.setTeam(command.getTeam());
        existingDriver.setPoints(command.getPoints());
        existingDriver.setNationality(command.getNationality());

        return repository.save(existingDriver);
    }
}