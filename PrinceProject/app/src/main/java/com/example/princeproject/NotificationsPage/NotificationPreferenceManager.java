package com.example.princeproject.NotificationsPage;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Class to handle the permissions for a user to recieve notifications
 */
public class NotificationPreferenceManager {
    private FirebaseFirestore db;


    public NotificationPreferenceManager() {
        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Changes the notification preference of the user
     * @param userId
     * @param allowNotifications
     */
    // From chatgpt, openai, "write a java implementation of function that set the user notification preference attach is the image of the db", 2024-11-08
    public void setNotificationPreference(String userId, boolean allowNotifications) {
        db.collection("users").document(userId)
                .update("Allow Notification", allowNotifications)
                .addOnSuccessListener(aVoid -> {
                    // Successfully updated the preference (no user notification)
                })
                .addOnFailureListener(e -> {
                    // Handle failure silently or log if necessary
                    e.printStackTrace();
                });
    }
}
