package com.example.princeproject.AdminPage;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
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

import java.util.List;

/**
 * This is a class that handles the list of events for admins to view and modify
 * */
public class AdminEventAdapter extends ArrayAdapter<Event> {
    private Context context;
    private List<Event> events;
    private FirebaseFirestore db;

    /**
     * Constructor for the event adapter
     * @param context
     *      The context of the event list
     * @param events
     *      The list of events for admins to view
     * @param db
     *      The database instance
     * */
    public AdminEventAdapter(Context context, List<Event> events, FirebaseFirestore db) {
        super(context, 0, events);
        this.context = context;
        this.events = events;
        this.db = db;
    }

    /**
     * Get the view of each event item for an admin
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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_event_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.user_name_text);
        TextView descriptionTextView = convertView.findViewById(R.id.user_role_text);
        TextView locationTextView = convertView.findViewById(R.id.user_email_text);
        Button deleteButton = convertView.findViewById(R.id.delete_button_event);
        ImageView eventPoster = convertView.findViewById(R.id.event_image);
        TextView removePoster = convertView.findViewById(R.id.remove_picture_text);
        TextView removeQR = convertView.findViewById(R.id.remove_qr_text);

        Event event = events.get(position);
        titleTextView.setText(event.getTitle());
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(event.getEventId(),position);
            }
        });

        removePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePoster(event.getEventId(),position);
            }
        });

        removeQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQR(event.getEventId());
            }
        });


        return convertView;
    }

    /**
     * Method to handle the deleting of QR hash data of a selected event
     * @param eventId
     *      The id of the event being modified
     * */
    public void deleteQR(String eventId) {
        db.collection("events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String qrData = documentSnapshot.getString("qrHashData");
                    if (qrData != null) {
                        db.collection("events").document(eventId)
                                .update("qrHashData",null)
                                .addOnSuccessListener(x -> {
                                    Toast.makeText(context,"QR Hash Data removed",Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(context,"This event does not have hashed data",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Method to handle the deleting of a selected event
     * @param eventId
     *      The id of the event being modified
     * @param position
     *      The position of the selected event in the list of events
     * */
    public void deleteEvent(String eventId,int position) {
        db.collection("events").document(eventId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    events.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Event deleted",Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Method to handle the deleting of picture of a selected event
     * @param eventId
     *      The id of the event being modified
     * @param position
     *      The position of the selected event in the list of events
     * */
    public void deletePoster(String eventId,int position) {
        db.collection("events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String eventPosterEncode = documentSnapshot.getString("eventPosterEncode");
                    if (eventPosterEncode != null) {
                        db.collection("events").document(eventId)
                                .update("eventPosterEncode",null)
                                .addOnSuccessListener(x -> {
                                    Event event = events.get(position);
                                    event.setImageEncode(null);

                                    notifyDataSetChanged();

                                    Toast.makeText(context,"Poster removed",Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(context,"Cannot remove a default event poster",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
