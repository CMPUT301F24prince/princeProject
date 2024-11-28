package com.example.princeproject.ProfilePage.EntrantListPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.princeproject.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ManageEventsActivity extends AppCompatActivity {
    private String eventId;
    private TextView eventNameText;
    private TextView eventDescriptionText;
    private TextView eventMaxParticipantsText;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);

        eventId = getIntent().getStringExtra("EVENT_ID");

        eventNameText = findViewById(R.id.event_name_text);
        eventDescriptionText = findViewById(R.id.event_description_text);
        eventMaxParticipantsText = findViewById(R.id.event_maxParticipants_text);

        db.collection("events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String description = documentSnapshot.getString("description");
                        int maxParticipants = ((Number)documentSnapshot.get("maxParticipants")).intValue();

                        eventNameText.setText(name);
                        eventDescriptionText.setText(description);
                        eventMaxParticipantsText.setText(String.valueOf(maxParticipants));
                    }
                });

        Button editButton = findViewById(R.id.manage_events_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditEventDialog();
            }
        });

    }

    private void showEditEventDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.manage_event_dialog, null);

        final EditText nameEdit = dialogView.findViewById(R.id.edit_event_name);
        final EditText descriptionEdit = dialogView.findViewById(R.id.edit_event_description);
        final EditText maxParticipantsEdit = dialogView.findViewById(R.id.edit_event_max_participants);


        nameEdit.setText(eventNameText.getText().toString());
        descriptionEdit.setText(eventDescriptionText.getText().toString());
        maxParticipantsEdit.setText(eventMaxParticipantsText.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Event")
                .setView(dialogView)
                .setPositiveButton("Save",(dialog, which) -> {

                    String updatedName = nameEdit.getText().toString().trim();
                    String updatedMaxParticipants = maxParticipantsEdit.getText().toString().trim();
                    String updatedDescription = descriptionEdit.getText().toString().trim();

                    if (updatedName.isEmpty() || updatedMaxParticipants.isEmpty() || updatedDescription.isEmpty()) {
                        Toast.makeText(ManageEventsActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Map<String, Object> updatedEvent = new HashMap<>();
                    updatedEvent.put("name", updatedName);
                    updatedEvent.put("maxParticipants", Integer.parseInt(updatedMaxParticipants));
                    updatedEvent.put("description", updatedDescription);

                    db.collection("events").document(eventId)
                            .update(updatedEvent)
                            .addOnSuccessListener(x ->{
                                eventNameText.setText(updatedName);
                                eventMaxParticipantsText.setText(updatedMaxParticipants);
                                eventDescriptionText.setText(updatedDescription);
                            });

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        builder.create().show();
    }



}
