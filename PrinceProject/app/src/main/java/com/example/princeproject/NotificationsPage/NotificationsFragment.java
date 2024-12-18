package com.example.princeproject.NotificationsPage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.example.princeproject.MainActivity;
import com.example.princeproject.NewUserActivity;
import com.example.princeproject.R;
import com.example.princeproject.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a class that handles the notifications page for the user. Here, the user will see any
 * notifications relevant to them regarding events they have signed up for
 * */
public class NotificationsFragment extends Fragment {

    private ListView notificationList;
    private ArrayList<Notification> notificationDataList;
    private NotificationArrayAdapter notificationAdapter;

    private NotificationPreferenceManager notificationPreferenceManager;
    private Switch notificationToggle;

    private String deviceId;
    private FirebaseFirestore db;

    /**
     * Method to initialize the creation of the Notification page
     * @param inflater
     *      LayoutInflator to get the notification fragment view
     * @param container
     *      Parameter to inflate the notification fragment view
     * @param savedInstanceState
     *      The current state of the view
     * @return
     *      The notification view
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    /**
     * Method to handle the notification page
     * @param view
     *      The notification view
     * @param savedInstanceState
     *      The current state of the view
     * */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();

        notificationToggle = view.findViewById(R.id.notification_toggle);
        //getUser(view);
        // Get the device ID
        deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        // Initialize Firestore and Notification Preference Manager

        notificationPreferenceManager = new NotificationPreferenceManager();

        // Set up the toolbar
        Toolbar toolbar = view.findViewById(R.id.notificationToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notifications");

        // Initialize the ListView and adapter
        notificationList = view.findViewById(R.id.notificationList);
        notificationDataList = new ArrayList<>();
        notificationAdapter = new NotificationArrayAdapter(this.getContext(), notificationDataList);
        notificationList.setAdapter(notificationAdapter);



        getNotifications();

    }

    /**
     * Gets all the users notifications every time the NotificationsFragment is visible
     */
    @Override
    public void onResume() {
        super.onResume();
        getNotifications();
    }

    /**
     * Method to get the current user
     */
    private String getCurrentUserId() {
        return Settings.Secure.getString(requireContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Method to set up the notification toggle for a user to accept notifications or not
     * @param view
     *      The current view
     */
    private void setupNotificationToggle(View view) {
        // Set up listener for the toggle
        notificationToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String userId = getCurrentUserId();
            notificationPreferenceManager.setNotificationPreference(userId, isChecked);

            // Display feedback message to the user
            String message = isChecked ? "Notifications Enabled" : "Notifications Disabled";
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Method to query all existing notifications tied to a user
     */
    private void getNotifications() {
        db.collection("notifications")
                .whereEqualTo("userId", deviceId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.e("Notifications", "Listen failed: ", e);
                        return;
                    }

                    notificationDataList.clear();
                    if (queryDocumentSnapshots != null) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String id = document.getId();
                            String title = document.getString("title");
                            String details = document.getString("details");
                            String location = document.getString("location");
                            String eventId = document.getString("eventId");
                            String userId = document.getString("userId");
                            Boolean recieved = document.getBoolean("recieved");
                            Boolean isInvite = document.getBoolean("isInvite");
                            if (isInvite == null) {
                                isInvite = true;
                            }

                            Notification notification = new Notification(id,title, details, location, userId, eventId, isInvite);
                            notificationDataList.add(notification);
                            //if (Boolean.FALSE.equals(recieved)) {
                            //    notification.sendAndroidNotification(getContext());
                            //}
                        }
                        notificationAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * Method to send push notifications to a user
     * @param context
     *      The current context
     * @param channelId
     *      The channel ID for the users device
     * @param id
     *      The id of the notification object being pushed
     */
    public void sendPushNotification(Context context, String channelId, int id) {
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), channelId);
        Intent ii = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("lmao");
        bigText.setBigContentTitle("Today's Bible Verse");
        bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("You've been invited");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(android.app.Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(id, mBuilder.build());
    }

}
