package com.example.princeproject.EventsPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.princeproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EventsFragment extends Fragment {

    private EventArrayAdapter arrayAdapter;
    private ListView eventFeed;
    private ArrayList<Event> eventList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        eventList = new ArrayList<>();
        arrayAdapter = new EventArrayAdapter(view.getContext(), eventList);

        eventFeed = view.findViewById(R.id.event_feed);
        eventFeed.setAdapter(arrayAdapter);

        Button addEventButton = view.findViewById(R.id.create_event_button);
        addEventButton.setOnClickListener(v -> getUserInput());
    }

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

            // Create a new event and add it to the list
            Event newEvent = new Event(title, description, startDate, endDate, location, maxParticipants, null, true);
            eventList.add(newEvent);
            arrayAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}