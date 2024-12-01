package com.example.princeproject;

import android.os.Bundle;
import android.provider.Settings;

import android.widget.ListView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import java.util.List;

/**
 * Class for the waiting list view for organizers to see
 */
public class WaitlistViewActivity extends AppCompatActivity {

    private ListView waitlistEventsListView;
    private WaitlistViewAdapter waitlistViewAdapter;
    private List<String> waitlistEvents; // Map of eventId to eventTitle
    private List<String> eventIds;              // List of eventIds for ordering
    private FirebaseFirestore db;
    String userId;

    /**
     * Method to initialize the waiting list view
     * @param savedInstanceState
     *      The current state of the view
     * */
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

    /**
     * Loads the all the events waitlisted by the current user
     */
    void loadWaitlistedEvents() {
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
