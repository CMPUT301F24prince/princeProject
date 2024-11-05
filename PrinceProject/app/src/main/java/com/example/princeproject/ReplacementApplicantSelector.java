package com.example.princeproject;

import android.util.Log;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Random;

public class ReplacementApplicantSelector {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void selectReplacementApplicant(String eventId) {
        // Reference to the specific event document by eventId
        db.collection("events")
                .whereEqualTo("eventId", eventId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        DocumentReference eventRef = querySnapshot.getDocuments().get(0).getReference();

                        eventRef.get().addOnSuccessListener(documentSnapshot -> {
                            List<String> waitingList = (List<String>) documentSnapshot.get("waiting");

                            if (waitingList != null && !waitingList.isEmpty()) {
                                // Select a random person from the waiting list
                                String selectedPerson = waitingList.get(new Random().nextInt(waitingList.size()));

                                // Update the Firestore document by adding the person to "chosen" and removing from "waiting"
                                eventRef.update(
                                        "chosen", FieldValue.arrayUnion(selectedPerson),
                                        "waiting", FieldValue.arrayRemove(selectedPerson)
                                ).addOnSuccessListener(aVoid -> {
                                    Log.d("ReplacementApplicant", "Replacement applicant selected successfully!");
                                }).addOnFailureListener(e -> {
                                    Log.e("ReplacementApplicant", "Error selecting replacement applicant: " + e.getMessage());
                                });
                            } else {
                                Log.d("ReplacementApplicant", "No applicants in the waiting list.");
                            }
                        }).addOnFailureListener(e -> {
                            Log.e("ReplacementApplicant", "Error fetching waiting list: " + e.getMessage());
                        });
                    } else {
                        Log.d("ReplacementApplicant", "Event not found with the specified eventId.");
                    }
                }).addOnFailureListener(e -> {
                    Log.e("ReplacementApplicant", "Error fetching event document: " + e.getMessage());
                });
    }
}
