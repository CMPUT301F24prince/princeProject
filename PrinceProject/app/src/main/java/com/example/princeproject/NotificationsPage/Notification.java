package com.example.princeproject.NotificationsPage;

/**
 * This is a class that defines a notification object
 * */
public class Notification {
    private String name;
    private String details;

    private String location;

    /**
     * Constructor for the notification object
     * @param name
     *      Name/title of the notification
     * @param details
     *      Details/description of the notification
     * */
    public Notification(String name, String details){
        this.name = name;
        this.details = details;
    }

    /**
     * Constructor for the notification object (with location)
     * @param name
     *      Name/title of the notification
     * @param details
     *      Details/description of the notification
     * @param location
     *      Location details of the notification
     * */
    public Notification(String name, String details, String location) {
        this.name = name;
        this.details = details;
        this.location = location;
    }

    /**
     * Getter for the notification name
     * @return
     *      The name of the notification
     * */
    public String getName() {
        return name;
    }

    /**
     * Setter for the notification name
     * @param name
     *      The name of the notification
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the notification details
     * @return
     *      The details of the notification
     * */
    public String getDetails() {
        return details;
    }


    /**
     * Getter for the notification location
     * @return
     *      The location of the notification
     * */
    public String getLocation() {return this.location; }

    /**
     * Setter for the notification details
     * @param details
     *      The details of the notification
     * */
    public void setDetails(String details) {
        this.details = details;
    }
}
