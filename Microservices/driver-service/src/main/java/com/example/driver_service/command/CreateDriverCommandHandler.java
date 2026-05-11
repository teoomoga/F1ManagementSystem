package com.example.driver_service.command;

import com.example.driver_service.model.entity.Driver;
import com.example.driver_service.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateDriverCommandHandler implements CommandHandler<CreateDriverCommand, Driver> {

    @Autowired
    private DriverRepository repository;

    @Override
    public Driver handle(CreateDriverCommand command) {
        if (command.getName() == null || command.getName().isEmpty()) {
            throw new RuntimeException("Numele pilotului nu poate fi gol!");
        }

        Driver driver = new Driver();
        driver.setName(command.getName());
        driver.setTeam(command.getTeam());
        driver.setPoints(command.getPoints());
        driver.setNationality(command.getNationality());

        return repository.save(driver);
    }
}