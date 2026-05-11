package com.example.driver_service.command;

import com.example.driver_service.model.entity.Driver;
import lombok.Data;

@Data
public class UpdateDriverCommand implements Command<Driver> {
    private Long id;
    private String name;
    private String team;
    private int points;
    private String nationality;
}