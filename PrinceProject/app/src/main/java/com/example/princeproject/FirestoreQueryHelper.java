package com.example.princeproject;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirestoreQueryHelper {
    private static FirebaseFirestore db;
    private static CollectionReference eventsRef;

    public static void getEntrantListData (
            String arrayField,
            List<String> targetList,
            ArrayAdapter<String> adapter
    ) {
        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("events");

        eventsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore", error.toString());
                    return;
                }
                if (value != null) {
                    targetList.clear();
                    for(QueryDocumentSnapshot doc: value){
                        List<String> chosenArray = (List<String>) doc.get(arrayField);
                        if (chosenArray != null) {
                            targetList.addAll(chosenArray);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
