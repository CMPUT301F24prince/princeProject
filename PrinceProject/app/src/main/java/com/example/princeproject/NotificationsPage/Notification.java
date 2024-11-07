package com.example.princeproject.NotificationsPage;

public class Notification {
    private String name;
    private String details;

    private String location;

    public Notification(String name, String details){
        this.name = name;
        this.details = details;
    }

    public Notification(String name, String details, String location) {
        this.name = name;
        this.details = details;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public String getLocation() {return this.location; }

    public void setDetails(String details) {
        this.details = details;
    }
}
