package com.example.princeproject;

public class Facility {

    private String organizer_id;
    private String location;
    private String name;
    private String description;

    public Facility(String organizer_id, String location, String name, String description) {
        this.organizer_id = organizer_id;
        this.location = location;
        this.name = name;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganizer_id() {
        return organizer_id;
    }

    public void setOrganizer_id(String organizer_id) {
        this.organizer_id = organizer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
