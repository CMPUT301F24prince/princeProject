package com.example.princeproject.ProfilePage.EntrantListPage;

import com.example.princeproject.R;
import com.example.princeproject.NotificationsPage.EventManager;

import android.content.Context;
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
import java.util.List;

public class EntrantListActivity extends AppCompatActivity {
    private Spinner eventSelection;
    private List<String> events = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String deviceId;
    private String eventId;
    private boolean lotteryDrawn = false;

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

        // Set up event selection spinner
        eventSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eventId = events.get(i);
                updateFragment(eventId);
                getLotteryDrawnStatus(eventId, status -> {
                    lotteryDrawn = status;
                    setupButton(lotteryButton);
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eventId = events.get(0);
                updateFragment(eventId);
                getLotteryDrawnStatus(eventId, status -> {
                    lotteryDrawn = status;
                    setupButton(lotteryButton);
                });
            }
        });
    }

    /**
     * Interface to handle callbacks to get the lottery status, enables proper updating of lotteryDrawn
     */
    private interface getLotteryStatusCallback {
        void onGetLotteryStatus(boolean status);
    }

    /**
     * Gets the status of the lottery for the given event
     * @param eventId Id of the given event
     * @param callback callback to enable updating of lotteryDrawn
     */
    private void getLotteryDrawnStatus(String eventId, getLotteryStatusCallback callback) {
        db.collection("events").whereEqualTo("eventId", eventId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        Boolean lotteryDrawnValue = doc.getBoolean("lotteryDrawn");
                        callback.onGetLotteryStatus(lotteryDrawnValue != null && lotteryDrawnValue);
                    } else {
                        callback.onGetLotteryStatus(false);
                    }
                });

    }

    /**
     * Sets up the button to change behaviour from drawing the lottery to drawing a replacement
     * based on the value of lotteryDrawn
     * @param lotteryButton the button being set up
     */
    private void setupButton(Button lotteryButton) {
        if (!lotteryDrawn) {
            lotteryButton.setText("Draw Lottery");
            lotteryButton.setOnClickListener(view -> {
                boolean lotterySuccess = SampleLottery(EntrantListActivity.this);
                if (lotterySuccess) {
                    lotteryDrawn = true;
                    db.collection("events").document(eventId).update("lotteryDrawn", true);
                }
            });
        } else {
            lotteryButton.setText("Draw Replacement");
            lotteryButton.setTextSize(14);
            lotteryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventManager.selectRandomEntrant(eventId);
                }
            });
        }
    }

    /**
     * Performs the lottery selection and updates the database accordingly.
     * @param context current context
     * @return returns true if the lottery was a success, false otherwise
     */
    public boolean SampleLottery(Context context) {
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
                                handler.postDelayed(() -> EventManager.selectRandomEntrant(eventId), delay);
                            }

                            Toast.makeText(context, "Successfully sampled " + iterations + " entrants!", Toast.LENGTH_SHORT).show();
                            db.collection("events").document(eventId).update("lotteryDrawn", true);
                        }
                    } else {
                        Toast.makeText(context, "No document found with eventId: " + eventId, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to fetch event data: " + e.getMessage(), Toast.LENGTH_LONG).show());

        return true;
    }

    /**
     * Updates the fragments to change the list being shown to match the given eventId
     * @param eventId eventId of the given event
     */
    private void updateFragment(String eventId) {
        PageAdapter adapter = new PageAdapter(this, eventId);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
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
