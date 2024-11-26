package com.example.princeproject.NotificationsPage;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.princeproject.R;
import com.example.princeproject.MainActivity;
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

    public static void sendPushNotification(Context context, String channelId, int id) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);
        notificationBuilder.setAutoCancel(true);

        notificationBuilder.setSmallIcon(R.drawable.filler_image);
        notificationBuilder.setTicker("This is a ticker");
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentTitle("Title");
        notificationBuilder.setContentText("Here is the text");

        Intent intent = new Intent(context, com.example.princeproject.MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id, notificationBuilder.build());
    }

    /**
     * Uploads a notification to the database,
     * @param userId
     * deviceId of the
     */
    public static void sendNotification(String userId, Context context) {
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

        //This may cause a problem with notifications hacing the same id
        sendPushNotification(context, "prince_notifications", notificationData.hashCode());
    }

    /**
     * Selects a random user in the waiting list and moves them to the chosen list. They recieve a notification
     * @param eventId
     */
    // From chatgpt, openai, "write a java implementation of selecting a random entrant from waitlist and remove them from waitlist and put them in chosen list attach is the image of the db, 2024-11-08
    public static void selectRandomEntrant(String eventId, Context context) {
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
                    sendNotification(selectedEntrant, context);

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
