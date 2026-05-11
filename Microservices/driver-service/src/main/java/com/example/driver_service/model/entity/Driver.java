package com.example.driver_service.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String team;
    private int points;
    private String nationality;

    public Driver(String name, String team, int points, String nationality) {
        this.name = name;
        this.team = team;
        this.points = points;
        this.nationality = nationality;
    }
}