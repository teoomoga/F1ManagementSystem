package org.example.model.service.export;

import org.example.model.entity.Driver;
import java.util.List;

public interface ExportStrategy {
    void export(List<Driver> drivers, String fileName);
}