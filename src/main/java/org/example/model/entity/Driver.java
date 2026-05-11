package org.example.model.entity;

public class Driver {
    private int id;
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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}