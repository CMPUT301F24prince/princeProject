package com.example.princeproject.NotificationsPage;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
public class EventManager {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Uploads a notification to the database,
     * @param userId
     * deviceId of the
     */
    public static void sendNotification(String userId) {
        // Create the notification details
        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("userId", userId);
        notificationData.put("title", "Congratulations!");
        notificationData.put("details", "You've been chosen for the event!");
        notificationData.put("timestamp", System.currentTimeMillis());

        // Save the notification in Firestore under the "notifications" collection
        db.collection("notifications").add(notificationData)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Notification sent to user: " + userId);
                })
                .addOnFailureListener(e -> {
                    System.err.println("Failed to send notification: " + e.getMessage());
                });
    }

    /**
     * Selects a random user in the waiting list and moves them to the chosen list. They recieve a notification
     * @param eventId
     */
    // From chatgpt, openai, "write a java implementation of selecting a random entrant from waitlist and remove them from waitlist and put them in chosen list attach is the image of the db, 2024-11-08
    public static void selectRandomEntrant(Context context, String eventId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("events").whereEqualTo("eventId", eventId);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                List<String> waitingList = (List<String>) documentSnapshot.get("waiting");
                List<String> chosenList = (List<String>) documentSnapshot.get("chosen");

                if (waitingList != null && !waitingList.isEmpty()) {
                    if (chosenList == null) {
                        chosenList = new ArrayList<>();
                    }

                    Random random = new Random();
                    int randomIndex = random.nextInt(waitingList.size());
                    String selectedEntrant = waitingList.get(randomIndex);

                    // Send a notification to the selected entrant
                    sendNotification(selectedEntrant);

                    waitingList.remove(randomIndex);
                    chosenList.add(selectedEntrant);

                    documentSnapshot.getReference().update("waiting", waitingList, "chosen", chosenList)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(context, "Entrant moved successfully.", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Error moving entrant: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                } else {
                    Toast.makeText(context, "Waiting list is empty.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "No document found with eventId: " + eventId, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(context, "Failed to fetch event data: " + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

}
