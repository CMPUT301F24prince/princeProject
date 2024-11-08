package com.example.princeproject.EventsPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.princeproject.Event;
import com.example.princeproject.R;

import java.util.ArrayList;

public class EventArrayAdapter extends ArrayAdapter<Event> {
    public EventArrayAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the event at the current position
        Event event = getItem(position);

        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.eventlist_entry, parent, false);
        } else {
            view = convertView;
        }

        // Find the TextViews in eventlist_entry.xml
        TextView titleTextView = view.findViewById(R.id.title_text);
        TextView descriptionTextView = view.findViewById(R.id.description_text);
        TextView locationTextView = view.findViewById(R.id.location_text);

        // Set the text for the TextViews from the Event object
        if (event != null) {
            titleTextView.setText(event.getTitle());
            descriptionTextView.setText(event.getDescription());
            locationTextView.setText(event.getLocation());
        }



        return view;
    }
}