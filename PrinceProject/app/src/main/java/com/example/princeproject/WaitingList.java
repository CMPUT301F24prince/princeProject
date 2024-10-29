package com.example.princeproject;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaitingList {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference waitingListRef = db.collection("waitingList");

    public void joinWaitingList(String userId) {
        Map<String, Object> userStatus = new HashMap<>();
        userStatus.put("status", "joined");

        waitingListRef.document(userId).set(userStatus)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User joined the waiting list"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error joining waiting list", e));
    }
}


