package com.example.princeproject;

import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FacilityActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private String deviceId;

    private TextView facilityName;
    private TextView facilityDescription;
    private TextView facilityLocation;
    private Button editFacilityButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);

        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        db = FirebaseFirestore.getInstance();
        facilityName = findViewById(R.id.facility_name_text);
        facilityDescription = findViewById(R.id.facility_description_text);
        facilityLocation = findViewById(R.id.facility_location_text);
        getFacilityDetails();

        editFacilityButton = findViewById(R.id.edit_facility_button);

        editFacilityButton.setOnClickListener(v -> {
            showEditFacilityDialog();
        });

    }

    private void getFacilityDetails() {
        db.collection("facilities").document(deviceId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String location = documentSnapshot.getString("location");
                        String description = documentSnapshot.getString("description");

                        facilityName.setText(name);
                        facilityLocation.setText(location);
                        facilityDescription.setText(description);

                    }
                });
    }

    private void showEditFacilityDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.facility_dialog_fragment, null);

        final EditText nameEditText = dialogView.findViewById(R.id.facility_name);
        final EditText locationEditText = dialogView.findViewById(R.id.facility_location);
        final EditText descriptionEditText = dialogView.findViewById(R.id.facility_description);

        nameEditText.setText(facilityName.getText().toString());
        locationEditText.setText(facilityLocation.getText().toString());
        descriptionEditText.setText(facilityDescription.getText().toString());


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Facility")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {

                    String updatedName = nameEditText.getText().toString().trim();
                    String updatedLocation = locationEditText.getText().toString().trim();
                    String updatedDescription = descriptionEditText.getText().toString().trim();

                    if (updatedName.isEmpty() || updatedLocation.isEmpty() || updatedDescription.isEmpty()) {
                        Toast.makeText(FacilityActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    Map<String, Object> updatedFacility = new HashMap<>();
                    updatedFacility.put("name", updatedName);
                    updatedFacility.put("location", updatedLocation);
                    updatedFacility.put("description", updatedDescription);

                    updateEventLocation(updatedLocation);


                    db.collection("facilities").document(deviceId)
                            .update(updatedFacility)
                            .addOnSuccessListener(aVoid -> {
                                facilityName.setText(updatedName);
                                facilityLocation.setText(updatedLocation);
                                facilityDescription.setText(updatedDescription);

                            });

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        builder.create().show();
    }

    private void updateEventLocation(String location) {
        db.collection("events").whereEqualTo("organizer",deviceId).get().addOnSuccessListener(queryDocumentSnapshots -> {
           for (DocumentSnapshot doc: queryDocumentSnapshots) {
               String eventId = doc.getId();

               db.collection("events").document(eventId)
                       .update("location",location)
                       .addOnSuccessListener(x -> {
                          Toast.makeText(FacilityActivity.this,"Event locations updated successfully",Toast.LENGTH_SHORT).show();
                       });
           }
        });
    }


}
