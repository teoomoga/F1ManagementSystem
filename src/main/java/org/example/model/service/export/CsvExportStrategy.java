package org.example.model.service.export;

import org.example.model.entity.Driver;
import java.io.PrintWriter;
import java.util.List;

public class CsvExportStrategy implements ExportStrategy {
    @Override
    public void export(List<Driver> drivers, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName + ".csv")) {
            writer.println("ID,Nume,Echipa,Puncte,Nationalitate");
            for (Driver d : drivers) {
                writer.println(d.getId() + "," + d.getName() + "," + d.getTeam() + "," + d.getPoints() + "," + d.getNationality());
            }
            System.out.println("Export CSV finalizat!");
        } catch (Exception e) { e.printStackTrace(); }
    }
}