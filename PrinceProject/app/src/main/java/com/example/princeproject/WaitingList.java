package com.example.princeproject;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WaitingList {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference eventsRef = db.collection("events");

    /**
     * Adds apecified user to a waiting list
     * @param eventId
     * event whose waiting list will be added to
     * @param userId
     * user id that will be added to list
     */
    public void joinWaitingList(String eventId, String userId) {
        // Query to find the event document with the specific eventId field value
        eventsRef.whereEqualTo("eventId", eventId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentReference eventRef = queryDocumentSnapshots.getDocuments().get(0).getReference();

                        // Add the user to the waiting list array
                        eventRef.update("waiting", FieldValue.arrayUnion(userId))
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("User added to waiting list successfully!");
                                })
                                .addOnFailureListener(e -> {
                                    System.err.println("Error adding user to waiting list: " + e.getMessage());
                                });
                    } else {
                        System.err.println("No event found with eventId: " + eventId);
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error fetching event document: " + e.getMessage());
                });
    }

    public void joinChosenList(String eventId, String userId) {
        // Query to find the event document with the specific eventId field value
        eventsRef.whereEqualTo("eventId", eventId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentReference eventRef = queryDocumentSnapshots.getDocuments().get(0).getReference();

                        // Add the user to the waiting list array
                        eventRef.update("chosen", FieldValue.arrayUnion(userId))
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("User added to waiting list successfully!");
                                })
                                .addOnFailureListener(e -> {
                                    System.err.println("Error adding user to waiting list: " + e.getMessage());
                                });
                    } else {
                        System.err.println("No event found with eventId: " + eventId);
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error fetching event document: " + e.getMessage());
                });
    }

    /**
     * Removes a user from their waiting list
     * @param eventId
     * Event whose list will be removed from
     * @param userName
     * User that will be removed
     */
    public void unjoinWaitingList(String eventId, String userName) {
        // Query to find the event document with the specific eventId field value
        eventsRef.whereEqualTo("eventId", eventId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentReference eventRef = queryDocumentSnapshots.getDocuments().get(0).getReference();

                        // Remove the user from the waiting list array
                        eventRef.update("waiting", FieldValue.arrayRemove(userName))
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("User removed from waiting list successfully!");
                                })
                                .addOnFailureListener(e -> {
                                    System.err.println("Error removing user from waiting list: " + e.getMessage());
                                });
                    } else {
                        System.err.println("No event found with eventId: " + eventId);
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error fetching event document: " + e.getMessage());
                });
    }
}