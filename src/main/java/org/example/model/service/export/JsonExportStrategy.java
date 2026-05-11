package org.example.model.service.export;

import org.example.model.entity.Driver;
import java.io.PrintWriter;
import java.util.List;

public class JsonExportStrategy implements ExportStrategy {
    @Override
    public void export(List<Driver> drivers, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName + ".json")) {
            writer.println("[");
            for (int i = 0; i < drivers.size(); i++) {
                Driver d = drivers.get(i);
                writer.println("  {");
                writer.println("    \"id\": " + d.getId() + ",");
                writer.println("    \"name\": \"" + d.getName() + "\",");
                writer.println("    \"team\": \"" + d.getTeam() + "\",");
                writer.println("    \"points\": " + d.getPoints() + ",");
                writer.println("    \"nationality\": \"" + d.getNationality() + "\"");
                writer.print("  }" + (i < drivers.size() - 1 ? "," : ""));
                writer.println();
            }
            writer.println("]");
            System.out.println("Export JSON finalizat!");
        } catch (Exception e) { e.printStackTrace(); }
    }
}