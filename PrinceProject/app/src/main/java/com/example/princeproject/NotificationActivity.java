package com.example.princeproject;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    private ListView notificationList;
    private ArrayList<Notification> notificationDataList;
    private NotificationArrayAdapter notificationAdapter;

    private String deviceId;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Get the device ID
        deviceId = getIntent().getStringExtra("deviceId");

        //Initialize the database
        db = FirebaseFirestore.getInstance();

        //Set the view of the notification activity
        setContentView(R.layout.activity_notifications);

        //Show the toolbar
        Toolbar toolbar = findViewById(R.id.notificationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notifications");


        //Set the view of the notification list and initialize the adapter
        notificationList = findViewById(R.id.notificationList);
        notificationDataList = new ArrayList<>();
        notificationAdapter = new NotificationArrayAdapter(this, notificationDataList);
        notificationList.setAdapter(notificationAdapter);

        //Retrieve notifications tied to the deviceId
        getNotifications();

    }

    private void addNotification(Notification notification){
        //Add notification to local array
        notificationDataList.add(notification);
        notificationAdapter.notifyDataSetChanged();

        //Add notification to database
        Map<String,Object> notif = new HashMap<>();
        notif.put("deviceId",deviceId);
        notif.put("title",notification.getName());
        notif.put("details",notification.getDetails());
        notif.put("timestamp",System.currentTimeMillis());
        db.collection("notifications")
                .add(notif);

    }

    private void deleteNotification(Notification notification){
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

    private void getNotifications(){
        db.collection("notifications")
                .whereEqualTo("deviceId", deviceId)
                //.orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            String title = document.getString("title");
                            String details = document.getString("details");
                            Notification notification = new Notification(title, details);
                            Toast.makeText(NotificationActivity.this, "Notif found: Adding to list", Toast.LENGTH_SHORT).show();
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
