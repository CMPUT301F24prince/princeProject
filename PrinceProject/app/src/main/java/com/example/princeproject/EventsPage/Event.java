package com.example.princeproject.EventsPage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.princeproject.R;
import com.example.princeproject.User;

public class Event {
    // Event details
    private String title;
    private String description;
    private Date registerDate;
    private Date eventDate;
    private String location;
    private int maxParticipants;
    private String eventId;

    // Organizer info
    private User organizer;

    // Pooling system
    private List<User> waitingList;
    private List<User> selectedParticipants;
    private String image_encode;

    // Status and check-ins
    private boolean isOpenForRegistration;

    // Constructors

    /**
     * Event constructor
     * @param eventId
     * Should be unique to every event
     * @param title
     * Event name
     * @param description
     * Event description
     * @param registerDate
     * @param eventDate
     * @param location
     * @param maxParticipants
     * @param organizer
     * deviceId of organizer
     * @param isOpenForRegistration
     */
    public Event(String eventId,String title, String description, Date registerDate, Date eventDate, String location, int maxParticipants, User organizer, boolean isOpenForRegistration) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.registerDate = registerDate;
        this.eventDate = eventDate;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.organizer = organizer;
        this.isOpenForRegistration = isOpenForRegistration;
    }

    /**
     * Constructs a new Event with the specified details, registration status, and image.
     *
     * @param eventId               A unique identifier for the event.
     * @param title                 The title of the event.
     * @param description           A brief description of the event.
     * @param registerDate             The start date and time of the event.
     * @param eventDate               The end date and time of the event.
     * @param location              The location where the event will take place.
     * @param maxParticipants       The maximum number of participants allowed for the event.
     * @param organizer             The User object representing the event's organizer.
     * @param isOpenForRegistration Whether the event is currently open for participant registration.
     * @param image_encode          A Base64 encoded string of the event's image.
     */
    public Event(String eventId,String title, String description, Date registerDate, Date eventDate, String location, int maxParticipants, User organizer, boolean isOpenForRegistration, String image_encode) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.registerDate = registerDate;
        this.eventDate = eventDate;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.organizer = organizer;
        this.isOpenForRegistration = isOpenForRegistration;
        this.image_encode = image_encode;
    }

    public Event(String eventId,String title,String description, String location, String image_encode) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.image_encode = image_encode;
    }

    public Event(String image_encode, String eventId) {
        this.eventId = eventId;
        this.image_encode = image_encode;
    }

    //Blank Constructor
    public Event() {
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

    public Date getregisterDate() {
        return registerDate;
    }

    public void setregisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date geteventDate() {
        return eventDate;
    }

    public void seteventDate(Date eventDate) {
        this.eventDate = eventDate;
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

    public String getImageEncode() {return image_encode;}

    public void setImageEncode(String image_encode){
        this.image_encode = image_encode;
    }


    public String getEventId() {
        return eventId;
    }
    

    /**
     * Decodes the Base64 encoded image string into a Bitmap and stores it as a file.
     *
     * @param context The application context used to access file storage.
     * @return A Uri object pointing to the saved image file, or null if there is no encoded image.
     */
    public android.net.Uri decodeBase64String(Context context) {
        Calendar calendar = Calendar.getInstance();
        String hour = Integer.toString( calendar.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString( calendar.get(Calendar.MINUTE));
        String second = Integer.toString( calendar.get(Calendar.SECOND));
        String milisecond = Integer.toString( calendar.get(Calendar.MILLISECOND));

        if (!(this.image_encode == null)) {
            byte[] decodedBytes = Base64.decode(this.image_encode, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            File outputFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_"+ hour+minute+second+milisecond+".png");
            try {
                FileOutputStream fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (FileNotFoundException e) {
            }

            //if (outputFile.exists()) {
            //    outputFile.delete();
            //}
            return Uri.fromFile(outputFile);
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.event_filler_image);

            File outputFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_"+hour+minute+second+milisecond+".png");
            try {
                FileOutputStream fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Uri.fromFile(outputFile);
        }
    }
}