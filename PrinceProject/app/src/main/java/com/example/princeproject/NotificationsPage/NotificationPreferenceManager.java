package com.example.princeproject.NotificationsPage;

import com.google.firebase.firestore.FirebaseFirestore;

public class NotificationPreferenceManager {
    private FirebaseFirestore db;

    public NotificationPreferenceManager() {
        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();
    }

    // Method to update notification preference
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