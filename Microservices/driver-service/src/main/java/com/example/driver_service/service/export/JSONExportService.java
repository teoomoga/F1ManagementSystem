package com.example.driver_service.service.export;

import com.example.driver_service.model.entity.Driver;
import org.springframework.stereotype.Service;

@Service
public class JSONExportService extends DriverExportTemplate {
    @Override
    protected String buildHeader() { return "[\n"; }

    @Override
    protected String buildDriverRow(Driver d) {
        return String.format("  {\"id\": %d, \"name\": \"%s\", \"points\": %d},\n",
                d.getId(), d.getName(), d.getPoints());
    }

    @Override
    protected String buildFooter() { return "]"; }
}