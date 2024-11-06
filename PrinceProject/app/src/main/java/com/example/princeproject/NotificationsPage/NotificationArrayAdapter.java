package com.example.princeproject.NotificationsPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.princeproject.EventsPage.Event;
import com.example.princeproject.R;

import java.util.ArrayList;

public class NotificationArrayAdapter extends ArrayAdapter<Notification> {
    private NotificationPreferenceManager notificationPreferenceManager;
    public NotificationArrayAdapter(Context context, ArrayList<Notification> notifications) {
        super(context, 0, notifications);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the event at the current position
        Notification notification = getItem(position);

        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.notification_list_entry, parent, false);
        } else {
            view = convertView;
        }

        // Find the TextViews in eventlist_entry.xml
        TextView titleTextView = view.findViewById(R.id.noti_title_text);
        TextView descriptionTextView = view.findViewById(R.id.noti_description_text);
        TextView locationTextView = view.findViewById(R.id.noti_location_text);
        Button optOutButton = view.findViewById(R.id.optOutButton);

        // Set the text for the TextViews from the Event object
        if (notification.subject_event != null) {
            titleTextView.setText(notification.subject_event.getTitle());
            descriptionTextView.setText(notification.subject_event.getDescription());
            locationTextView.setText(notification.subject_event.getLocation());
        }

        optOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with actual user ID from Firestore
                String userId = "9620cce4e7c896f8";

                // Set Allow Notification to false
                notificationPreferenceManager.setNotificationPreference(userId, false);
            }
        });



        return view;
    }
}
