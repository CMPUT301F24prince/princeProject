package com.example.princeproject.ProfilePage.EntrantListPage;

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

/**
 * Class to help retrieve user data when they are selected for an event
 * */
public class FirestoreQueryHelper {
    private static FirebaseFirestore db;
    private static CollectionReference eventsRef;

    /**
     * helper function to query the data base and get all of the entrants list data
     * @param arrayField the specific field to query (accepted, chosen, declined)
     * @param targetList list to store the gathered data
     * @param adapter array adapter for the list
     * @param eventId specified eventId to only gather the data from specific event
     */
    public static void getEntrantListData (
            String arrayField,
            List<String> targetList,
            EntrantAdapter adapter,
            String eventId
    ) {
        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("events");

        // makes sure that the eventId is equal to the eventId received
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
                        // gets the array from the database and stores it as entrantArray
                        List<String> entrantArray = (List<String>) doc.get(arrayField);
                        if (entrantArray != null) {
                            targetList.addAll(entrantArray);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * helper function to query the database and get all the events that correlate to an organizer
     * @param context the context of EntrantListActivity used to create the array adapter
     * @param organizerId id of the organizer (current user)
     * @param events list to store all of the events (ids) of the current organizer
     * @param eventSelection spinner object used to select the event they want to view
     */
    public static void getOrganizerEvents (
            Context context,
            String organizerId,
            List<String> events,
            Spinner eventSelection
    ){
        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("events");
        // new list to store the names of the events for the spinner
        List<String> eventNames = new ArrayList<>();

        // finds every event that is organized by the current user
        eventsRef.whereEqualTo("organizer",organizerId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        // gets the eventId and the event name and add the to their respective parallel lists
                        String event = (String) doc.get("eventId");
                        String eventName = (String) doc.get("name");
                        events.add(event);
                        eventNames.add(eventName);
                    }
                    // creates the spinner with the data retrieved
                    ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,eventNames);
                    eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    eventSelection.setAdapter(eventAdapter);
                });


    }
}
