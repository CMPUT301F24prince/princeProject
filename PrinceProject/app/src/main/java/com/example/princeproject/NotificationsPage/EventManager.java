package com.example.princeproject.NotificationsPage;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
public class EventManager {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public static void selectRandomEntrant(String eventId) {
        db = FirebaseFirestore.getInstance();
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
                            .addOnSuccessListener(aVoid -> System.out.println("Entrant moved successfully."))
                            .addOnFailureListener(e -> System.err.println("Error moving entrant: " + e.getMessage()));
                } else {
                    System.out.println("Waiting list is empty.");
                }
            } else {
                System.out.println("No document found with eventId: " + eventId);
            }
        }).addOnFailureListener(e -> System.err.println("Failed to fetch event data: " + e.getMessage()));
    }
}