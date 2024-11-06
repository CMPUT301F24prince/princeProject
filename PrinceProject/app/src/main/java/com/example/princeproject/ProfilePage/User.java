package com.example.princeproject.ProfilePage;

import android.provider.Settings;
import android.content.Context;

public class User {
    private String deviceId;
    private String email;
    private String name;
    private int phoneNumber;
    private boolean organizer;
    private boolean admin;

    public User(Context context, String email, String name, int phoneNumber) {
        this.deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.organizer = false;
        this.admin = false;
    }

    //getters and setters
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isOrganizer() {
        return this.organizer;
    }

    public void makeOrganizer() {
        this.organizer = true;
    }

    public void demoteOrganizer() {
        this.organizer = false;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void makeAdmin() {
        this.admin = true;
    }

    public void demoteAdmin() {
        this.admin = false;
    }
}
