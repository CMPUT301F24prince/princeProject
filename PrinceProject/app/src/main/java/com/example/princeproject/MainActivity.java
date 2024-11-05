package com.example.princeproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private WaitingList WaitingList;
    private ReplacementApplicantSelector replacementApplicantSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        WaitingList= new WaitingList();
        replacementApplicantSelector = new ReplacementApplicantSelector();

        Button joinWaitingListButton = findViewById(R.id.joinWaitingListButton);
        Button unjoinWaitingListButton = findViewById(R.id.unjoinWaitingListButton);
        Button drawReplacementButton = findViewById(R.id.drawReplacementButton);

        String eventId = "1";
        String applicantName = "John Doe";

        // Set up listeners for each button
        joinWaitingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitingList.joinWaitingList(eventId, applicantName);
            }
        });

        unjoinWaitingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitingList.unjoinWaitingList(eventId, applicantName);
            }
        });

        drawReplacementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replacementApplicantSelector.selectReplacementApplicant(eventId);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}