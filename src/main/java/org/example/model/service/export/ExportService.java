package org.example.model.service.export;

import org.example.model.entity.Driver;
import java.util.List;

public class ExportService {
    private ExportStrategy strategy;

    public void setStrategy(ExportStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeExport(List<Driver> drivers, String fileName) {
        if (strategy != null) {
            strategy.export(drivers, fileName);
        }
    }
}