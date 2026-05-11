package com.example.driver_service.command;

import com.example.driver_service.model.entity.Driver;
import lombok.Data;

@Data
public class CreateDriverCommand implements Command<Driver> {
    private String name;
    private String team;
    private int points;
    private String nationality;
}