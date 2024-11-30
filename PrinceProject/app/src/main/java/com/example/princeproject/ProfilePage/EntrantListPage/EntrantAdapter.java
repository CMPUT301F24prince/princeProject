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
import java.util.List;

public class EntrantAdapter extends BaseAdapter {
    private Context context;
    private List<String> entrants;
    private LayoutInflater inflater;
    private String type;
    private String eventId;

    public EntrantAdapter(Context context, List<String> entrants, String type,String eventId) {
        this.context = context;
        this.entrants = entrants;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
        this.eventId = eventId;
    }

    @Override
    public int getCount() {
        return entrants.size();
    }

    @Override
    public Object getItem(int position) {
        return entrants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

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

    public void cancelEntrant(String entrant, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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

                    eventRef.update("chosen", chosenList, "declined", declinedList)
                            .addOnSuccessListener(aVoid -> {
                                entrants.remove(entrant);
                                notifyDataSetChanged();

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
