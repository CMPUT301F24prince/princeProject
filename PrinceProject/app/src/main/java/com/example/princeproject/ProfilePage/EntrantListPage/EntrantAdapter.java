package com.example.princeproject.ProfilePage.EntrantListPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.princeproject.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a class that handles the list of entrants for an event
 * */
public class EntrantAdapter extends BaseAdapter {
    private Context context;
    private List<String> entrants;
    private LayoutInflater inflater;
    private String type;
    private String eventId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Constructor for the event adapter
     * @param context
     *      The context of the entrants list
     * @param entrants
     *      The list of entrants for an event
     * @param type
     *      The type of entrants
     * @param eventId
     *      The event id
     * */
    public EntrantAdapter(Context context, List<String> entrants, String type,String eventId) {
        this.context = context;
        this.entrants = entrants;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
        this.eventId = eventId;
    }

    /**
     * Test function to get the size of entrants
     * @return
     *      The size of the entrants list
     * */
    @Override
    public int getCount() {
        return entrants.size();
    }

    /**
     * Test function to get an entrant from the list
     * @param position
     *      The position of the selected entrant in the list of entrants
     * @return
     *      The entrant selected
     * */
    @Override
    public Object getItem(int position) {
        return entrants.get(position);
    }

    /**
     * Test function to get the id of the entrant
     * @param position
     *      The position of the selected entrant in the list of entrants
     * @return
     *      The entrant selected
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get the view of entrants for an event
     * @param position
     *      The position of the selected entrant
     * @param convertView
     *      The view to switch to on selection
     * @param parent
     *      The view of the list of events
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_entrant, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.entrant_name);
        Button button = convertView.findViewById(R.id.cancel_button);

        String entrant = entrants.get(position);
        textView.setText(entrant);

        // Set the button visibility based on the type
        if ("chosen".equals(type)) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }

        button.setOnClickListener(v -> {
            cancelEntrant(entrant,position);
        });

        return convertView;
    }

    /**
     * Send a cancel notification to entrants when an event is cancelled
     * @param eventId
     *      The event being cancelled
     * @param userId
     *      The userId to send the notification to
     * @param eventName
     *      The name of the event
     * */
    public void sendCancelNotification(String userId,String eventName,String eventId) {
        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("userId", userId);
        notificationData.put("title", "Sorry!");
        notificationData.put("details", "Unfortunately, the organizer cancelled your application to: "+eventName);
        notificationData.put("timestamp", System.currentTimeMillis());
        notificationData.put("eventId",eventId);
        notificationData.put("received", false);

        // Save the notification in Firestore under the "notifications" collection
        db.collection("notifications").add(notificationData);
    }

    /**
     * Cancel an entrant from the list of entrants for an event
     * @param entrant
     *      The entrant being removed from the list
     * @param position
     *      The position of the entrant in the list
     * */
    public void cancelEntrant(String entrant, int position) {

        DocumentReference eventRef = db.collection("events").document(eventId);

        eventRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Get the current "chosen" and "declined" lists
                List<String> chosenList = (List<String>) documentSnapshot.get("chosen");
                List<String> declinedList = (List<String>) documentSnapshot.get("declined");

                if (chosenList != null && chosenList.contains(entrant)) {
                    // Remove the entrant from "chosen" list
                    chosenList.remove(entrant);

                    // Add the entrant to the "declined" list
                    if (declinedList == null) {
                        declinedList = new ArrayList<>();
                    }
                    declinedList.add(entrant);
                    String eventName = documentSnapshot.getString("name");

                    eventRef.update("chosen", chosenList, "declined", declinedList)
                            .addOnSuccessListener(aVoid -> {
                                entrants.remove(entrant);
                                notifyDataSetChanged();
                                sendCancelNotification(entrant,eventName,eventId);

                                Toast.makeText(context, "Entrant moved to declined", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Failed to move entrant: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });
    }
}
