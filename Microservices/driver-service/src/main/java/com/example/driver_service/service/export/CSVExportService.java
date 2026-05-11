package com.example.driver_service.service.export;
import com.example.driver_service.model.entity.Driver;
import org.springframework.stereotype.Service;

@Service
public class CSVExportService extends DriverExportTemplate {
    @Override
    protected String buildHeader() { return "ID,Nume,Echipa,Puncte\n"; }

    @Override
    protected String buildDriverRow(Driver d) {
        return d.getId() + "," + d.getName() + "," + d.getTeam() + "," + d.getPoints() + "\n";
    }

    @Override
    protected String buildFooter() { return ""; }
}
