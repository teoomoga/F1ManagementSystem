package com.example.driver_service.controller;

import com.example.driver_service.model.entity.Driver;
import com.example.driver_service.repository.DriverRepository;
import com.example.driver_service.service.export.CSVExportService;
import com.example.driver_service.service.export.JSONExportService;
import com.example.driver_service.service.export.XMLExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/drivers/queries")
public class DriverQueryController {

    @Autowired
    private DriverRepository repository;

    @Autowired
    private CSVExportService csvService;
    @Autowired
    private JSONExportService jsonService;
    @Autowired
    private XMLExportService xmlExportService;

    @GetMapping("/all")
    public List<Driver> getAllDrivers() {
        return repository.findAll();
    }

    @GetMapping("/standings")
    public List<Driver> getStandings() {
        return repository.findAllByOrderByPointsDesc();
    }

    @GetMapping("/team/{teamName}")
    public List<Driver> getDriversByTeam(@PathVariable String teamName) {
        return repository.findByTeamContainingIgnoreCase(teamName);
    }

    @GetMapping(value = "/export/{format}", produces = "text/html")
    public String exportStandings(@PathVariable String format) {
        List<Driver> drivers = repository.findAllByOrderByPointsDesc();
        String result = "";

        if (format.equalsIgnoreCase("csv")) {
            result = csvService.export(drivers);
        } else if (format.equalsIgnoreCase("json")) {
            result = jsonService.export(drivers);
        } else if (format.equalsIgnoreCase("xml")) {
            result = xmlExportService.export(drivers);
        } else {
            return "Unknown format";
        }

        return "<html><body><pre>" + result + "</pre></body></html>";
    }
}