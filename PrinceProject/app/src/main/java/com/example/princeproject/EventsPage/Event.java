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

/**
 * Class to represent an event object
 * */
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


    /**
     * Constructs a new Event with the specified details
     *
     * @param eventId               A unique identifier for the event.
     * @param title                 The title of the event.
     * @param description           A brief description of the event.
     * @param location              The location where the event will take place.
     * @param image_encode          A Base64 encoded string of the event's image.
     */
    public Event(String eventId,String title,String description, String location, String image_encode) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.image_encode = image_encode;
    }

    /**
     * Constructs an event object with a provided id and image
     *
     * @param eventId               A unique identifier for the event.
     * @param image_encode          A Base64 encoded string of the event's image.
     */
    public Event(String image_encode, String eventId) {
        this.eventId = eventId;
        this.image_encode = image_encode;
    }

    /**
     * Blank constructor for an event object
     * */
    public Event() {
    }

    /**
     * Getter for event title
     * */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for setting event title
     * @param title
     *      Title of the event
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for event description
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for setting event description
     * @param description
     *      Description of event
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for getting event registration deadline
     * */
    public Date getregisterDate() {
        return registerDate;
    }

    /**
     * Setter for setting event registration deadline
     * @param registerDate
     *      The registration deadline for the event
     * */
    public void setregisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * Getter for getting event date
     * */
    public Date geteventDate() {
        return eventDate;
    }

    /**
     * Setter for setting event date
     * @param eventDate
     *      The date of the event
     * */
    public void seteventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Getter for getting event location
     * */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for setting event location
     * @param location
     *      The location of the event
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for getting max participants
     * */
    public int getMaxParticipants() {
        return maxParticipants;
    }

    /**
     * Setter for setting event max participants
     * @param maxParticipants
     *      The maximum number of participants allowed for an event
     * */
    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    /**
     * Getter for getting event organizer
     * */
    public User getOrganizer() {
        return organizer;
    }

    /**
     * Setter for setting event organizer
     * @param organizer
     *      The organizer of the event
     * */
    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    /**
     * Getter for getting event waiting list
     * */
    public List<User> getWaitingList() {
        return waitingList;
    }

    /**
     * Setter for setting waiting list
     * @param waitingList
     *      The waiting list
     * */
    public void setWaitingList(List<User> waitingList) {
        this.waitingList = waitingList;
    }

    /**
     * Getter for getting selected participants list
     * */
    public List<User> getSelectedParticipants() {
        return selectedParticipants;
    }

    /**
     * Setter for setting selected participants
     * @param selectedParticipants
     *      The selected participants for an event
     * */
    public void setSelectedParticipants(List<User> selectedParticipants) {
        this.selectedParticipants = selectedParticipants;
    }

    /**
     * Getter for getting open event flag
     * */
    public boolean isOpenForRegistration() {
        return isOpenForRegistration;
    }

    /**
     * Setter for setting event open flag
     * @param openForRegistration
     *      The flag to indicate if an event is open or not
     * */
    public void setOpenForRegistration(boolean openForRegistration) {
        isOpenForRegistration = openForRegistration;
    }

    /**
     * Getter for getting event image
     * */
    public String getImageEncode() {return image_encode;}

    /**
     * Setter for setting event image
     * @param image_encode
     *      The encoded image string
     * */
    public void setImageEncode(String image_encode){
        this.image_encode = image_encode;
    }

    /**
     * Getter for getting event id
     * */
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
            return null;
        }
    }
}