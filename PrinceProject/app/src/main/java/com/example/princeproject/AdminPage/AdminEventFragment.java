package com.example.princeproject.AdminPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.princeproject.EventsPage.Event;
import com.example.princeproject.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminEventFragment extends Fragment {
    private ListView eventListView;
    private List<Event> events;
    private FirebaseFirestore db;
    private AdminEventAdapter adminEventAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        db = FirebaseFirestore.getInstance();
        eventListView = view.findViewById(R.id.event_list_view);
        events = new ArrayList<>();

        adminEventAdapter = new AdminEventAdapter(getContext(),events,db);
        eventListView.setAdapter(adminEventAdapter);

        getEvents();
    }

    public void getEvents() {
        db.collection("events").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    events.clear();
                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        String event_id = (String) doc.get("eventId");
                        String event_title = (String) doc.get("name");
                        String event_desc = (String) doc.get("description");
                        String event_location = (String) doc.get("location");
                        String image_encode = (String) doc.get("eventPosterEncode");

                        Event event = new Event(event_id,event_title,event_desc,event_location,image_encode);
                        events.add(event);
                    }
                    adminEventAdapter.notifyDataSetChanged();
                });
    }
}
