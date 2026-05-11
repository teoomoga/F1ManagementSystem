package com.example.driver_service.service.export;

import com.example.driver_service.model.entity.Driver;
import java.util.List;

public abstract class DriverExportTemplate {

    public final String export(List<Driver> drivers) {
        StringBuilder sb = new StringBuilder();
        sb.append(buildHeader());

        for (Driver d : drivers) {
            sb.append(buildDriverRow(d));
        }

        sb.append(buildFooter());
        return sb.toString();
    }

    protected abstract String buildHeader();
    protected abstract String buildDriverRow(Driver driver);
    protected abstract String buildFooter();
}