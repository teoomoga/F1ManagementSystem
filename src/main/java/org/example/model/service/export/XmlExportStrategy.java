package org.example.model.service.export;

import org.example.model.entity.Driver;
import java.io.PrintWriter;
import java.util.List;

public class XmlExportStrategy implements ExportStrategy {
    @Override
    public void export(List<Driver> drivers, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName + ".xml")) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<drivers>");
            for (Driver d : drivers) {
                writer.println("  <driver>");
                writer.println("    <id>" + d.getId() + "</id>");
                writer.println("    <name>" + d.getName() + "</name>");
                writer.println("    <team>" + d.getTeam() + "</team>");
                writer.println("    <points>" + d.getPoints() + "</points>");
                writer.println("    <nationality>" + d.getNationality() + "</nationality>");
                writer.println("  </driver>");
            }
            writer.println("</drivers>");
            System.out.println("Export XML finalizat!");
        } catch (Exception e) { e.printStackTrace(); }
    }
}