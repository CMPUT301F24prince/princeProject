package com.example.princeproject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is a class that defines a user account for the app
 * */
public class User implements Serializable {

    //Attributes for a user
    private String name;
    private String email;
    private String phone;
    private String account; //Account can be user, organizer, or admin
    private String deviceId;
    private ArrayList<String> organizedEventIds;

    /**
     * This is a constructor for creating a user object
     * @param name
     *      Name of the user
     * @param email
     *      Email of the user
     * @param phone
     *      Phone number of the user (Optional, can be null)
     * @param account
     *      Type of the account of the user (user, organizer, admin)
     * */
    public User(String name, String email, String phone, String account, String deviceId){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.account = account;
        this.deviceId = deviceId;
        this.organizedEventIds = new ArrayList<>();

    }

    /**
     * Gets the name of the user
     * @return
     *      Name of the user
     * */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user
     * @param name
     *      Name of the user
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user
     * @return
     *      Email of the user
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user
     * @param email
     *      Email of the user
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user
     * @return
     *      Phone number of the user
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user
     * @param phone
     *      Phone number of the user
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the account type of the user
     * @return
     *      Account type of the user
     * */
    public String getAccount() {
        return account;
    }

    /**
     * Sets the account type of the user
     * @param account
     *      Account type of the user
     * */
    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setOrganizedEventIds(ArrayList<String> organizedEventIds) { this.organizedEventIds = organizedEventIds; }

    public ArrayList<String> getOrganizedEventIds() { return this.organizedEventIds; }

    public void addOrganizedEventID(String eventID) {
        if (this.organizedEventIds == null) {
            this.organizedEventIds = new ArrayList<>();
        }
        this.organizedEventIds.add(eventID);
    }
}
