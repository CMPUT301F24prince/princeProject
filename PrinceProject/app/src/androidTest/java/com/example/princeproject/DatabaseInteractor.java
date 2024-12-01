package com.example.princeproject;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DatabaseInteractor {
    private final FirebaseFirestore db;

    public DatabaseInteractor(FirebaseFirestore firestore) {
        this.db = firestore;
    }

    public void storeDeviceIdForUser(String userId, String deviceId) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("deviceId", deviceId);
        db.collection("users").document(userId).set(data);
    }
}