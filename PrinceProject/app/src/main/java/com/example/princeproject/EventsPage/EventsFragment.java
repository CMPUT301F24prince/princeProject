package com.example.princeproject.EventsPage;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.princeproject.R;
import com.example.princeproject.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.firebase.firestore.CollectionReference;


public class EventsFragment extends Fragment {

    private final int GALLERY_REQ_CODE = 1000;
    private EventArrayAdapter arrayAdapter;
    private ListView eventFeed;
    private ImageButton invitesButton;
    private ArrayList<Event> eventList;
    private FirebaseFirestore db;
    private String username;
    private ImageView preview;
    private String poster_encode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        getUsername();
        eventList = new ArrayList<>();
        arrayAdapter = new EventArrayAdapter(view.getContext(), eventList);


        eventFeed = view.findViewById(R.id.event_feed);
        eventFeed.setAdapter(arrayAdapter);

        eventFeed.setOnItemClickListener((parent, v, position, id) -> {
                    new EventDialogFragment(arrayAdapter.getItem(position), username).show(getActivity().getSupportFragmentManager(), "Event");
                }
        );

        invitesButton = view.findViewById(R.id.invitesButton);

        Button addEventButton = view.findViewById(R.id.create_event_button);
        addEventButton.setOnClickListener(v -> getUserInput());

        ArrayList<Event> some_events = new ArrayList<Event>();
        getEvents(some_events);
    }

    /**
     * Creates fragment for creating events, adds it to the database
     */
    private void getUserInput() {
        // Create input fields for event details
        final EditText titleEditText = new EditText(getContext());
        titleEditText.setHint("Enter Title");

        final EditText descriptionEditText = new EditText(getContext());
        descriptionEditText.setHint("Enter Description");

        final EditText startDateEditText = new EditText(getContext());
        startDateEditText.setHint("Enter Start Date (yyyy-MM-dd)");

        final EditText endDateEditText = new EditText(getContext());
        endDateEditText.setHint("Enter End Date (yyyy-MM-dd)");

        final EditText locationEditText = new EditText(getContext());
        locationEditText.setHint("Enter Location");

        final EditText maxParticipantsEditText = new EditText(getContext());
        maxParticipantsEditText.setHint("Enter Max Participants");
        maxParticipantsEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        // Create a layout to hold the input fields
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(titleEditText);
        layout.addView(descriptionEditText);
        layout.addView(startDateEditText);
        layout.addView(endDateEditText);
        layout.addView(locationEditText);
        layout.addView(maxParticipantsEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Event");
        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String startDateStr = startDateEditText.getText().toString().trim();
            String endDateStr = endDateEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();
            String maxParticipantsStr = maxParticipantsEditText.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty() ||
                    location.isEmpty() || maxParticipantsStr.isEmpty()) {
                Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            int maxParticipants;
            try {
                maxParticipants = Integer.parseInt(maxParticipantsStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Max participants must be a valid number", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date startDate;
            Date endDate;
            try {
                startDate = dateFormat.parse(startDateStr);
                endDate = dateFormat.parse(endDateStr);
            } catch (ParseException e) {
                Toast.makeText(getContext(), "Dates must be in the format yyyy-MM-dd", Toast.LENGTH_SHORT).show();
                return;
            }
            String organizer = Settings.Secure.getString(requireContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            List<String> emptyList = new ArrayList<>();
            String eventId = generateEventId();

            // Create a new event and add it to the list
            Event newEvent = new Event(eventId,title, description, startDate, endDate, location, maxParticipants, null, true);
            Map<String, Object> eventDb = new HashMap<>();
            eventDb.put("name",title);
            eventDb.put("description",description);
            eventDb.put("startDate",startDate);
            eventDb.put("endDate",endDate);
            eventDb.put("location",location);
            eventDb.put("maxParticipants",maxParticipants);
            eventDb.put("organizer",organizer);
            eventDb.put("eventId",eventId);
            eventDb.put("accepted",emptyList);
            eventDb.put("chosen",emptyList);
            eventDb.put("declined",emptyList);
            eventDb.put("waiting",emptyList);
            eventDb.put("lotteryDrawn",false);

            db.collection("events").document(eventId).set(eventDb);

            eventList.add(newEvent);
            arrayAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode==GALLERY_REQ_CODE) {
                android.net.Uri imageUri = data.getData();
                preview.setImageURI(imageUri);

                InputStream imageStream = null;
                try {
                    imageStream = getContext().getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                }
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                String base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
                this.poster_encode = base64String;
            }


        }
    }

    public void addEventPoster(String imageBase64, String eventId) {
        // Query to find the event document with the specific eventId field value
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventsRef = db.collection("events");
        eventsRef.whereEqualTo("eventId", eventId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentReference eventRef = queryDocumentSnapshots.getDocuments().get(0).getReference();

                        eventRef.update("eventPosterEncode", imageBase64)
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("User added to waiting list successfully!");
                                })
                                .addOnFailureListener(e -> {
                                    System.err.println("Error adding user to waiting list: " + e.getMessage());
                                });
                    } else {
                        System.err.println("No event found with eventId: " + eventId);
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error fetching event document: " + e.getMessage());
                });
    }

    /**
     * Generates a random event id
     * @return Randomized event id
     */
    private String generateEventId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();

        StringBuilder eventId = new StringBuilder(20);
        for(int i = 0; i < 20; i++) {
            int index = random.nextInt(chars.length());
            eventId.append(chars.charAt(index));
        }
        return eventId.toString();
    }

    /**
     * Gets events in the database
     * @param events
     * Output parameter (for some reason), should be a blank array that will be passed to array adapter
     */
    private void getEvents (List<Event> events)
    {
        CollectionReference eventsRef = this.db.collection("events");

        // finds every event that is organized by the current user
        eventsRef
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        // gets the eventId and the event name and add the to their respective parallel lists
                        String event_id = (String) doc.get("eventId");
                        String event_name = (String) doc.get("name");
                        String event_desc = (String) doc.get("description");
                        String event_location = (String) doc.get("location");
                        int event_max = 20;
                        String event_organizer = (String) doc.get("organizer");
                        //more stuff will be added eventually

                        User user = new User(event_organizer, "","","","");
                        Date date1 = new Date();
                        Date date2 = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            date1 = dateFormat.parse("2003-09-20");
                            date2 = dateFormat.parse("2024-11-07");
                        }
                        catch (ParseException e) {
                        }
                        Event event = new Event(event_id,event_name, event_desc, date1, date2, event_location, event_max, user, true);

                        events.add(event);
                    }
                    this.arrayAdapter.addAll(events);
                    this.arrayAdapter.notifyDataSetChanged();
                });


    }

    /**
     * Gets username associated with the device id
     */
    private void getUsername() {
        CollectionReference UserRef = this.db.collection("users");
        UserRef.whereEqualTo("deviceId", Settings.Secure.getString(this.getContext().getContentResolver(), Settings.Secure.ANDROID_ID))
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        // gets the eventId and the event name and add the to their respective parallel lists
                        this.username = (String) doc.get("name");
                    }
                });

    }
}
