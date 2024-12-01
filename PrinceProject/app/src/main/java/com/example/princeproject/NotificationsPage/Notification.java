package com.example.princeproject.NotificationsPage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.princeproject.MainActivity;
import com.example.princeproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This is a class that defines a notification object
 * */
public class Notification {
    private String name;
    private String details;

    private String location;

    public String deviceId;

    public String eventId;
    private String id;
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
     * Constructor for the notification object
     * @param name
     *      Name/title of the notification
     * @param details
     *      Details/description of the notification
     * @param location
     *      Location details of the notification
     * @param userDeviceId
     *      The deviceId of the user
     * */
    public Notification(String name, String details, String location, String userDeviceId) {
        this.name = name;
        this.details = details;
        this.location = location;
        this.deviceId = userDeviceId;
    }

    /**
     * Constructor for the notification object (with event details)
     * @param id
     *      The random id for the notification
     * @param name
     *      Name/title of the notification
     * @param details
     *      Details/description of the notification
     * @param location
     *      Location details of the notification
     * @param userDeviceId
     *      The deviceId of the user
     * @param eventId
     *      The eventId for the notification to be sent for
     * */
    public Notification(String id,String name, String details, String location, String userDeviceId, String eventId) {
        this.name = name;
        this.details = details;
        this.location = location;
        this.deviceId = userDeviceId;
        this.eventId = eventId;
        this.id = id;
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

    /**
     * Getter for the notification deviceid
     * @return
     *      The deviceid of the notification
     * */
    public String getDeviceId() {return deviceId;}

    /**
     * Getter for the eventId
     * @return
     *      The eventId for the notification
     * */
    public String getEventId() {return eventId;}

    /**
     * Getter for the notification id
     * @return
     *      The id of the notification
     * */
    public String getId() {
        return id;
    }

    /**
     * Setter for the notification id
     * @param id
     *      The id of the notification
     * */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Change the status on whether a notification is recieved or not
     * @param newval
     *      The new flag value
     * */
    public void changeRecievedStatus(Boolean newval) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("notifications").document(this.id);

        docRef.update("received", newval)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override public void onSuccess(Void aVoid) {

                    }

        });
    }

    /**
     * Method to handle the logic for sending notifications to users
     * @param context
     *      The current context
     * */
    public void sendAndroidNotification(Context context) {
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "PrinceProject");
        Intent ii = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("You've been invited!");
        bigText.setBigContentTitle(this.name);
        bigText.setSummaryText(this.details);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("You've been invited!");
        mBuilder.setContentText(this.details);
        mBuilder.setPriority(android.app.Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    "PrinceProject",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId("PrinceProject");
        }

        mNotificationManager.notify(this.hashCode(), mBuilder.build());
        changeRecievedStatus(true);
    }
}
