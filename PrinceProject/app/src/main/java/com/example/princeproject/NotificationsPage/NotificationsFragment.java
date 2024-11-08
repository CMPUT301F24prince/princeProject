package com.example.princeproject.NotificationsPage;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.example.princeproject.EventsPage.EventArrayAdapter;
import com.example.princeproject.R;
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
        super.onCreate(savedInstanceState);
        thisview = view;
        //Get the device ID
        deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Initialize the database
        db = FirebaseFirestore.getInstance();

        //I'm not sure if I do this here
        //setContentView(R.layout.activity_notifications);

        //Show the toolbar
        Toolbar toolbar = view.findViewById(R.id.notificationToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notifications");


        //Set the view of the notification list and initialize the adapter
        notificationList = view.findViewById(R.id.notificationList);
        notificationDataList = new ArrayList<>();
        notificationAdapter = new NotificationArrayAdapter(this.getContext(), notificationDataList);
        notificationList.setAdapter(notificationAdapter);

        //Retrieve notifications tied to the deviceId
        getNotifications();

        notificationPreferenceManager = new NotificationPreferenceManager();

        // Find the button and set an onClick listener

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

    public void getNotifications(){
        db.collection("notifications")
                .whereEqualTo("recipientDeviceId", deviceId)
                //.orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            String title = document.getString("title");
                            String details = document.getString("details");
                            String location = document.getString("location");
                            Notification notification = new Notification(title, details, location);
                            Toast.makeText(thisview.getContext(), "Notif found: Adding to list", Toast.LENGTH_SHORT).show();
                            notificationDataList.add(notification);
                            notificationAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void testNotifs(){
        notificationDataList.add((new Notification("Test Name 1","Test Details 1")));
        notificationDataList.add((new Notification("Test Name 2","Test Details 2")));
        notificationDataList.add((new Notification("Test Name 3","Test Details 3")));
    }

}
