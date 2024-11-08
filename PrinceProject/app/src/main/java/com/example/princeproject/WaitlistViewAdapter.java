package com.example.princeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import java.util.List;

public class WaitlistViewAdapter extends ArrayAdapter<String> {

    private List<String> events;
    private List<String> eventIds;
    private FirebaseFirestore db;
    private String userId;

    /**
     * Constructor to create the adapter
     * @param context context of the adapter
     * @param events list of events by name
     * @param eventIds list of events by ids
     * @param userId id of the current user
     */
    public WaitlistViewAdapter(Context context, List<String> events,List<String> eventIds, String userId) {
        super(context, 0, eventIds);
        this.events = events;
        this.userId = userId;
        db = FirebaseFirestore.getInstance();
        this.eventIds = eventIds;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.waitlist_view_entry, parent, false);
        }

        String event = events.get(position);
        String eventId = eventIds.get(position);

        TextView eventTitleTextView = convertView.findViewById(R.id.eventTitleTextView);
        Button removeButton = convertView.findViewById(R.id.removeButton);

        eventTitleTextView.setText(event);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeUserFromWaitlist(eventId);
            }
        });

        return convertView;
    }

    /**
     * Removes the user from the selected waitlist
     * @param eventId id of the event in which the user is to be removed from
     */
    private void removeUserFromWaitlist(String eventId) {
        db.collection("events").document(eventId)
                .update("waiting", FieldValue.arrayRemove(userId))
                .addOnSuccessListener(aVoid -> {
                    // Remove event from list and notify adapter
                    int index = eventIds.indexOf(eventId);
                    events.remove(index);
                    eventIds.remove(index);
                    notifyDataSetChanged();
                });

    }
}
