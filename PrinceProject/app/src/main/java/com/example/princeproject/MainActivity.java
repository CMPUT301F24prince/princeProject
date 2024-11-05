package com.example.princeproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private NotificationPreferenceManager notificationPreferenceManager;
    private EventManager eventManager;
    private String targetEventId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        notificationPreferenceManager = new NotificationPreferenceManager();

        // Find the button and set an onClick listener
        Button optOutButton = findViewById(R.id.optOutButton);
        optOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with actual user ID from Firestore
                String userId = "9620cce4e7c896f8";

                // Set Allow Notification to false
                notificationPreferenceManager.setNotificationPreference(userId, false);
            }
        });


        eventManager = new EventManager();

        Button selectEntrantButton = findViewById(R.id.selectEntrantButton);
        selectEntrantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the selectRandomEntrant method in EventManager
                eventManager.selectRandomEntrant(targetEventId);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}