package com.example.princeproject;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaitlistViewActivity extends AppCompatActivity {

    private ListView waitlistEventsListView;
    private WaitlistViewAdapter waitlistViewAdapter;
    private List<String> waitlistEvents; // Map of eventId to eventTitle
    private List<String> eventIds;              // List of eventIds for ordering
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitlist_view);

        db = FirebaseFirestore.getInstance();
        userId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        waitlistEventsListView = findViewById(R.id.waitlistEventsListView);
        waitlistEvents = new ArrayList<>();
        eventIds = new ArrayList<>();
        waitlistViewAdapter = new WaitlistViewAdapter(this, waitlistEvents, eventIds, userId);
        waitlistEventsListView.setAdapter(waitlistViewAdapter);

        loadWaitlistedEvents();
    }

    private void loadWaitlistedEvents() {
        db.collection("events")
                .whereArrayContains("waiting", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    waitlistEvents.clear();
                    eventIds.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String eventName = document.getString("name");
                        String eventId = document.getString("eventId");
                        waitlistEvents.add(eventName);
                        eventIds.add(eventId);
                    }
                    waitlistViewAdapter.notifyDataSetChanged();
                });

    }


}
