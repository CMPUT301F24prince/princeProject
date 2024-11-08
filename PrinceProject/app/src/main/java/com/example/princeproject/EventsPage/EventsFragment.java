package com.example.princeproject.EventsPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.princeproject.Event;
import com.example.princeproject.R;
import com.example.princeproject.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.firebase.firestore.CollectionReference;

public class EventsFragment extends Fragment {

    private EventArrayAdapter arrayAdapter;
    private ListView eventFeed;
    private ImageButton invitesButton;
    private ArrayList<Event> eventList;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        eventList = new ArrayList<>();
        arrayAdapter = new EventArrayAdapter(view.getContext(), eventList);

        eventFeed = view.findViewById(R.id.event_feed);
        eventFeed.setAdapter(arrayAdapter);

        eventFeed.setOnItemClickListener((parent, v, position, id) -> {
            new EventDialogFragment(arrayAdapter.getItem(position)).show(getActivity().getSupportFragmentManager(), "Event");
        });

        invitesButton = view.findViewById(R.id.invitesButton);

        // Retrieve and display events
        ArrayList<Event> some_events = new ArrayList<Event>();
        getEvents(some_events);
    }

    private void getEvents(List<Event> events) {

        CollectionReference eventsRef = this.db.collection("events");

        // Retrieves all events from Firestore
        eventsRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String event_id = (String) doc.get("eventId");
                        String event_name = (String) doc.get("name");
                        String event_desc = (String) doc.get("description");
                        String event_location = (String) doc.get("location");
                        int event_max = 20;
                        String event_organizer = (String) doc.get("organizer");

                        User user = new User(event_organizer, "", "", "", "");
                        Event event = new Event(event_name, event_desc, new Date(), new Date(), event_location, event_max, user, true);

                        events.add(event);
                    }
                    this.arrayAdapter.addAll(events);
                    this.arrayAdapter.notifyDataSetChanged();
                });
    }
}
