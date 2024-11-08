package com.example.princeproject.ProfilePage;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.princeproject.Event;
import com.example.princeproject.EventsPage.EventArrayAdapter;
import com.example.princeproject.R;
import com.example.princeproject.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This is a class that handles the profile page for the user, where they can view their
 * inputted details.
 * */
public class ProfileFragment extends Fragment implements EditProfileFragment.EditProfileDialogListener {

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView accountTextView;
    private Button editProfile;
    private Button createEventButton;

    private String deviceId;
    private User currentUser;
    private FirebaseFirestore db;
    private CollectionReference eventsRef;

    private ListView organizedEventsListView;
    private ArrayList<Event> organizedEventsList;
    private EventArrayAdapter eventArrayAdapter;

    /**
     * Method to initialize the creation of the profile page
     * @param inflater
     *      LayoutInflator to get the profile fragment view
     * @param container
     *      Parameter to inflate the profile fragment view
     * @param savedInstanceState
     *      The current state of the view
     * @return
     *      The profile view
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /**
     * Method to handle the profile page
     * @param view
     *      The profile view
     * @param savedInstanceState
     *      The current state of the view
     * */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Set up database
        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("events");

        organizedEventsListView = view.findViewById(R.id.organized_events_list);
        organizedEventsList = new ArrayList<>();
        eventArrayAdapter = new EventArrayAdapter(view.getContext(), organizedEventsList);
        organizedEventsListView.setAdapter(eventArrayAdapter);

        deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        // Show the toolbar
        Toolbar toolbar = view.findViewById(R.id.notificationToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

        // Get the views
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        phoneTextView = view.findViewById(R.id.phoneTextView);
        accountTextView = view.findViewById(R.id.accountTextView);
        editProfile = view.findViewById(R.id.editProfileButton);

        getUserInfo();  // Initialize currentUser asynchronously

        editProfile.setOnClickListener(viewArg -> {
            EditProfileFragment fragment = EditProfileFragment.newInstance(currentUser);
            fragment.show(getChildFragmentManager(), "Edit Profile");
        });

        // Initialize Create Event button and set its listener
        createEventButton = view.findViewById(R.id.create_event_button);
        createEventButton.setOnClickListener(v -> createEvent());
    }

    private void createEvent() {
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
            Event newEvent = new Event(title, description, startDate, endDate, location, maxParticipants, currentUser, true);
            if (currentUser.getAccount().equals("User")) {
                currentUser.setAccount("Organizer");
            }

            currentUser.addOrganizedEventID(eventId);
            updateUserInfo(currentUser);

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

            db.collection("events").document(eventId).set(eventDb);

            // Add the event to the list
            organizedEventsList.add(newEvent);

            // Notify the adapter that the data has changed
            eventArrayAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

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

    private void getUserInfo() {
        db.collection("users")
                .whereEqualTo("deviceId", deviceId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String userName = document.getString("name");
                            String userEmail = document.getString("email");
                            String userPhone = document.getString("phone");
                            String userAccount = document.getString("account");

                            currentUser = new User(userName, userEmail, userPhone, userAccount, deviceId);
                            updateTextViews();
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e("ProfileFragment", "Error retrieving user data", e));
    }

    private void updateTextViews() {
        nameTextView.setText(currentUser.getName());
        emailTextView.setText(currentUser.getEmail());
        phoneTextView.setText(currentUser.getPhone());
        accountTextView.setText(currentUser.getAccount());
    }

    private void updateUserInfo(User user) {
        db.collection("users")
                .whereEqualTo("deviceId", deviceId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        DocumentReference userRef = document.getReference();
                        userRef.set(user);
                    }
                });
    }

    @Override
    public void onDialogPositiveClick(User user) {
        updateUserInfo(user);
        updateTextViews();
    }

    @Override
    public void setEditProfile(User user) {

    }
}

