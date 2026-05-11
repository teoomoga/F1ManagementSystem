package com.example.driver_service.service.export;

import com.example.driver_service.model.entity.Driver;
import org.springframework.stereotype.Service;

@Service
public class XMLExportService extends DriverExportTemplate {
    @Override
    protected String buildHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<drivers>\n";
    }

    @Override
    protected String buildDriverRow(Driver d) {
        return String.format(
                "  <driver>\n    <id>%d</id>\n    <name>%s</name>\n    <team>%s</team>\n    <points>%d</points>\n  </driver>\n",
                d.getId(), d.getName(), d.getTeam(), d.getPoints()
        );
    }

    @Override
    protected String buildFooter() {
        return "</drivers>";
    }
}