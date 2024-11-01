package com.example.princeproject;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreQueryHelper {
    private static FirebaseFirestore db;
    private static CollectionReference eventsRef;

    public static void getEntrantListData (
            String arrayField,
            List<String> targetList,
            ArrayAdapter<String> adapter,
            String eventId
    ) {
        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("events");

        eventsRef.whereEqualTo("eventId",eventId).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

    public static void getOrganizerEvents (
            Context context,
            String organizerId,
            List<String> events,
            Spinner eventSelection
    ){
        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("events");
        List<String> eventNames = new ArrayList<>();

        eventsRef.whereEqualTo("organizer",organizerId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        String event = (String) doc.get("eventId");
                        String eventName = (String) doc.get("name");
                        events.add(event);
                        eventNames.add(eventName);
                    }
                    ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,eventNames);
                    eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    eventSelection.setAdapter(eventAdapter);
                });


    }
}
