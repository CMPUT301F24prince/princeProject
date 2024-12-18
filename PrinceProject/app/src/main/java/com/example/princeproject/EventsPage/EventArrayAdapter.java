package com.example.princeproject.EventsPage;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.princeproject.R;

import java.util.ArrayList;

/**
 * Class to handle an array of events to display the list of events
 * */
public class EventArrayAdapter extends ArrayAdapter<Event> {
    /**
     *
     * @param context
     * Context of parent fragment/activity
     * @param events
     * All events that will showup in list view
     */
    public EventArrayAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    /**
     * Get the view of each event item
     * @param position
     *      The position of the selected event in the event list
     * @param convertView
     *      The view to switch to on selection
     * @param parent
     *      The view of the list of events
     * */
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
        TextView titleTextView = view.findViewById(R.id.user_name_text);
        TextView descriptionTextView = view.findViewById(R.id.user_role_text);
        TextView locationTextView = view.findViewById(R.id.user_email_text);
        ImageView eventPoster = view.findViewById(R.id.event_image);

        // Set the text for the TextViews from the Event object
        if (event != null) {
            titleTextView.setText(event.getTitle());
            descriptionTextView.setText(event.getDescription());
            locationTextView.setText(event.getLocation());

            titleTextView.setText(event.getTitle());
            String smth = event.getDescription();
            descriptionTextView.setText(event.getDescription());
            locationTextView.setText(event.getLocation());

            Uri poster_uri =  event.decodeBase64String(getContext());

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (!(poster_uri == null)) {
                        eventPoster.setImageURI(null);
                        eventPoster.setImageURI(poster_uri);
                    }
                }
            });
        }


        return view;
    }
}