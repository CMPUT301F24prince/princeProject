package com.example.princeproject.AdminPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.princeproject.EventsPage.Event;
import com.example.princeproject.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminEventAdapter extends ArrayAdapter<Event> {
    private Context context;
    private List<Event> events;
    private FirebaseFirestore db;

    public AdminEventAdapter(Context context, List<Event> events, FirebaseFirestore db) {
        super(context, 0, events);
        this.context = context;
        this.events = events;
        this.db = db;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_event_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.title_text);
        TextView descriptionTextView = convertView.findViewById(R.id.description_text);
        TextView locationTextView = convertView.findViewById(R.id.location_text);
        Button deleteButton = convertView.findViewById(R.id.delete_button_event);

        Event event = events.get(position);
        titleTextView.setText(event.getTitle());
        descriptionTextView.setText(event.getDescription());
        locationTextView.setText(event.getLocation());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(event.getEventId(),position);
            }
        });


        return convertView;
    }

    public void deleteEvent(String eventId,int position) {
        db.collection("events").document(eventId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    events.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Event deleted",Toast.LENGTH_SHORT).show();
                });
    }
}
