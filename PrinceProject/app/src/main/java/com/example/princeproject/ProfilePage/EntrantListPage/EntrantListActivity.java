package com.example.princeproject.ProfilePage.EntrantListPage;

import com.example.princeproject.R;
import com.example.princeproject.NotificationsPage.EventManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntrantListActivity extends AppCompatActivity {
    private Spinner eventSelection;
    private List<String> events = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String deviceId;
    private String eventId;
    private PageAdapter adapter;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrant_list);

        // Initialize views
        eventSelection = findViewById(R.id.event_spinner);
        Button lotteryButton = findViewById(R.id.lottery_button);
        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        // Fetch events for the organizer
        FirestoreQueryHelper.getOrganizerEvents(this, deviceId, events, eventSelection);

        Button manageEventButton = findViewById(R.id.manage_event_button);

        // Set up event selection spinner
        eventSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eventId = events.get(i);
                updateFragment(eventId);
                checkLotteryStatus(lotteryButton,eventId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eventId = events.get(0);
                updateFragment(eventId);
                checkLotteryStatus(lotteryButton,eventId);
            }
        });

        manageEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntrantListActivity.this, ManageEventsActivity.class);
                intent.putExtra("EVENT_ID",eventId);
                startActivity(intent);
            }
        });

    }

    private void checkLotteryStatus(Button button, String eventId) {
        db.collection("events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    boolean lotteryDrawn = documentSnapshot.getBoolean("lotteryDrawn");
                    if(lotteryDrawn) {
                        button.setText("Draw Replacement");
                        button.setOnClickListener(v -> EventManager.selectRandomEntrant(getApplicationContext(), eventId));
                    } else {
                        button.setText("Draw Lottery");
                        button.setOnClickListener(v -> SampleLottery(EntrantListActivity.this,button));
                    }
                });

    }

    public void sendLotteryLossNotification(String userId,String eventName,String eventId) {
        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("userId", userId);
        notificationData.put("title", "Sorry!");
        notificationData.put("details", "Unfortunately, you have lost the lottery for the event: "+eventName);
        notificationData.put("timestamp", System.currentTimeMillis());
        notificationData.put("eventId",eventId);
        notificationData.put("received", false);

        // Save the notification in Firestore under the "notifications" collection
        db.collection("notifications").add(notificationData);
    }


    /**
     * Performs the lottery selection and updates the database accordingly.
     * @param context current context
     */
    public void SampleLottery(Context context, Button button) {
        db.collection("events").whereEqualTo("eventId", eventId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        int maxParticipants = doc.getLong("maxParticipants").intValue();
                        List<String> waitingList = (List<String>) doc.get("waiting");

                        if (waitingList == null || waitingList.isEmpty()) {
                            Toast.makeText(context, "Waiting list is empty.", Toast.LENGTH_SHORT).show();
                        } else {
                            int waitingListSize = waitingList.size();
                            int iterations = Math.min(waitingListSize, maxParticipants);

                            Handler handler = new Handler(Looper.getMainLooper());
                            for (int i = 0; i < iterations; i++) {
                                int delay = i * 500;
                                handler.postDelayed(() -> EventManager.selectRandomEntrant(context,eventId), delay);
                            }

                            db.collection("events").document(eventId).get()
                                    .addOnSuccessListener(updatedDoc -> {
                                        List<String> newWaitingList = (List<String>) updatedDoc.get("waiting");
                                        String eventName = updatedDoc.getString("name");
                                        if (newWaitingList != null && !newWaitingList.isEmpty()) {
                                            // Send a notification to each user in the new waiting list
                                            for (String userId : newWaitingList) {
                                                sendLotteryLossNotification(userId, eventName,eventId );
                                            }
                                        }
                                    });

                            Toast.makeText(context, "Successfully sampled " + iterations + " entrants!", Toast.LENGTH_SHORT).show();
                            db.collection("events").document(eventId).update("lotteryDrawn", true);
                            checkLotteryStatus(button,eventId);
                        }
                    } else {
                        Toast.makeText(context, "No document found with eventId: " + eventId, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to fetch event data: " + e.getMessage(), Toast.LENGTH_LONG).show());

    }

    /**
     * Updates the fragments to change the list being shown to match the given eventId
     * @param eventId eventId of the given event
     */
    public void updateFragment(String eventId) {

        adapter = new PageAdapter(this, eventId);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                    case 0:
                        tab.setText("Chosen");
                        break;
                    case 1:
                        tab.setText("Declined");
                        break;
                    case 2:
                        tab.setText("Accepted");
                        break;
                    case 3:
                        tab.setText("Waiting");
                        break;
                }
            }).attach();

    }
}
