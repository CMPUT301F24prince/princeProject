package com.example.princeproject.EventsPage;

import java.util.Date;
import java.util.List;
import java.util.Random;
import com.example.princeproject.User;

public class Event {
    // Event details
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private int maxParticipants;

    // Organizer info
    private User organizer;

    // Pooling system
    private List<User> waitingList;
    private List<User> selectedParticipants;

    // Status and check-ins
    private boolean isOpenForRegistration;

    // Constructor
    public Event(String title, String description, Date startDate, Date endDate, String location, int maxParticipants, User organizer, boolean isOpenForRegistration) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.organizer = organizer;
        this.isOpenForRegistration = isOpenForRegistration;
    }

    // Getters and setters for all fields
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public List<User> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<User> waitingList) {
        this.waitingList = waitingList;
    }

    public List<User> getSelectedParticipants() {
        return selectedParticipants;
    }

    public void setSelectedParticipants(List<User> selectedParticipants) {
        this.selectedParticipants = selectedParticipants;
    }

    public boolean isOpenForRegistration() {
        return isOpenForRegistration;
    }

    public void setOpenForRegistration(boolean openForRegistration) {
        isOpenForRegistration = openForRegistration;
    }


}