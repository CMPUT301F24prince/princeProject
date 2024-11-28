package com.example.princeproject.NotificationsPage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
    private View thisview;
    private String targetEventId = "1";

    private NotificationPreferenceManager notificationPreferenceManager;
    private EventManager eventManager;
    private Switch notificationToggle;

    private String deviceId;
    private FirebaseFirestore db;
    private User user;

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
        getUser(view);
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

        notificationList.setOnItemClickListener((parent, v, position, id) -> {
            new NotificationListFragment(notificationAdapter.getItem(position)).show(getActivity().getSupportFragmentManager(), "Details");
        });

    }

    /**
     * Method to handle adding a notification to the listview of a user.
     * @param notification
     *      The notification to add
     * */
    public void addNotification(Notification notification){
        //Add notification to local array
        notificationDataList.add(notification);
        notificationAdapter.notifyDataSetChanged();

        //Add notification to database
        Map<String,Object> notif = new HashMap<>();
        notif.put("deviceId",deviceId);
        notif.put("title",notification.getName());
        notif.put("details",notification.getDetails());
        notif.put("timestamp",System.currentTimeMillis());
        db.collection("notifications").add(notif);
    }

    /**
     * Method to handle the deletion of a notification from the listview.
     * @param notification
     *      The notification to deleted
     * */
    public void deleteNotification(Notification notification){
        notificationDataList.remove(notification);
        notificationAdapter.notifyDataSetChanged();

        //Delete notification from database
        db.collection("notifications")
                .whereEqualTo("deviceId", deviceId)
                .whereEqualTo("title", notification.getName())
                .whereEqualTo("details", notification.getDetails())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            document.getReference().delete();
                        }
                    }
                });
    }

    private String getCurrentUserId() {
        return Settings.Secure.getString(requireContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

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

    private void getNotifications() {
        db.collection("notifications")
                .whereEqualTo("userId", user.getDeviceId())
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        System.err.println("Listen failed: " + e);
                        return;
                    }

                    // Clear the current list and update it with new data
                    notificationDataList.clear();
                    if (queryDocumentSnapshots != null) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String title = document.getString("title");
                            String details = document.getString("details");
                            String location = document.getString("location");
                            String userId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                            String eventId = document.getString("eventId");

                            Notification notification = new Notification(title, details, location, userId, eventId);
                            notificationDataList.add(notification);
                            sendPushNotification(getContext(), "PRINCE_CHANNEL_ID_NOTIFICATION", notification.hashCode());
                        }
                        notificationAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getUser(View view) {
        deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        db.collection("users")
                //Check if device id is in database
                .document(deviceId)
                .get()
                .addOnSuccessListener(document -> {
                    //If device is already enrolled, do nothing
                    if (document.exists()) {
                        //User already exists
                        user = new User(document.getString("name"),document.getString("email"),document.getString("phone"),document.getString("accountType"), deviceId);
                        notificationToggle.setChecked(document.getBoolean("Allow Notification"));
                    }
                    setupNotificationToggle(view);

                    if (notificationToggle.isChecked()) {
                        getNotifications();
                    }
                });
    }

    public void sendPushNotification(Context context, String channelId, int id) {
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
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
