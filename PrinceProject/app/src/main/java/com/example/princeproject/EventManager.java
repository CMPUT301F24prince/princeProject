package com.example.princeproject;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class EventManager {

    private FirebaseFirestore db;

    public EventManager() {
        db = FirebaseFirestore.getInstance();
    }

    public void selectRandomEntrant(String eventId) {
        // Query the events collection for the document with the specified eventId
        Query query = db.collection("events").whereEqualTo("eventId", eventId);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                // Assuming there is only one document for the given eventId
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                List<String> waitingList = (List<String>) documentSnapshot.get("waiting");
                List<String> chosenList = (List<String>) documentSnapshot.get("chosen");

                if (waitingList != null && !waitingList.isEmpty()) {
                    // Initialize the chosen list if it doesn't exist
                    if (chosenList == null) {
                        chosenList = new ArrayList<>();
                    }

                    // Select a random entrant from the waiting list
                    Random random = new Random();
                    int randomIndex = random.nextInt(waitingList.size());
                    String selectedEntrant = waitingList.get(randomIndex);

                    // Remove the selected entrant from waitingList and add to chosenList
                    waitingList.remove(randomIndex);
                    chosenList.add(selectedEntrant);

                    // Update Firestore document
                    documentSnapshot.getReference().update("waiting", waitingList, "chosen", chosenList)
                            .addOnSuccessListener(aVoid -> {
                                System.out.println("Entrant moved successfully.");
                            })
                            .addOnFailureListener(e -> {
                                System.err.println("Error moving entrant: " + e.getMessage());
                            });
                } else {
                    System.out.println("Waiting list is empty.");
                }
            } else {
                System.out.println("No document found with eventId: " + eventId);
            }
        }).addOnFailureListener(e -> {
            System.err.println("Failed to fetch event data: " + e.getMessage());
        });
    }
}
