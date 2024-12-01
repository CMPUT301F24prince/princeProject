package com.example.princeproject.EventsPage;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
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

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.princeproject.AdminPage.AdminActivity;
import com.example.princeproject.Facility;
import com.example.princeproject.R;
import com.example.princeproject.User;
import com.example.princeproject.WaitlistViewActivity;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.journeyapps.barcodescanner.CaptureActivity;

import com.google.firebase.firestore.CollectionReference;

import org.w3c.dom.Text;

/**
 * The {@code EventsFragment} class represents a UI fragment in an Android app where users can view,
 * create, and interact with events. It retrieves events from a Firestore database and displays them
 * in a {@link ListView}, allowing users to add new events with details such as title, date, location,
 * and image.
 */
public class EventsFragment extends Fragment {

    private final int GALLERY_REQ_CODE = 1000;
    private static final int QR_SCAN_REQ_CODE = 1001;
    private EventArrayAdapter arrayAdapter;
    private ListView eventFeed;
    private ImageButton invitesButton;
    private ArrayList<Event> eventList;
    private FirebaseFirestore db;
    private String username;
    private ImageView preview;
    private String poster_encode = null;
    private String deviceId;

    /**
     * Method to create view of the event fragment
     * @param inflater
     *      The inflater to display the layout
     * @param container
     *      The container for the events
     * @param savedInstanceState
     *      The current state of the events listview
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    /**
     * Method to initialize the view of the event fragment
     * @param view
     *      The view of the event
     * @param savedInstanceState
     *      The current state of the event
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
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

        Button waitlistButton = view.findViewById(R.id.waitlist_view);

        waitlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WaitlistViewActivity.class);
                startActivity(intent);
            }
        });

        Button adminPageButton = view.findViewById(R.id.admin_button);

        db.collection("users").document(deviceId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String accountType = documentSnapshot.getString("accountType");
                    if (!"Admin".equals(accountType)) {
                        adminPageButton.setVisibility(View.INVISIBLE);
                    }
                });

        adminPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

        Button scanQrButton = view.findViewById(R.id.scan_qr_utton);
        scanQrButton.setOnClickListener(v -> startQrScanner());


        Button addEventFacilityButton = view.findViewById(R.id.create_event_facility_button);

        checkFacilityStatus(addEventFacilityButton);

        ArrayList<Event> some_events = new ArrayList<Event>();
        getEvents(some_events);
    }

    /**
     * Check if a facility exists for an organizer. If it doesn't organizer must
     * create a faciltiy before creating event. If there is an existing facility, then
     * an event can be created for that facility
     * @param button
     *      The button object for organizers to click to create a facility/event
     * */
    private void checkFacilityStatus(Button button) {
        db.collection("facilities").document(deviceId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        button.setText("Create Event");
                        button.setOnClickListener(v -> getUserInput());
                    } else {
                        button.setText("Create Facility");
                        button.setOnClickListener(v -> createFacility(button));
                    }
                });
    }

    /**
     * Method to handle the creation of the facility
     * @param button
     *      The button object for organizers to click to create a facility
     * */
    private void createFacility(Button button) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.facility_dialog_fragment, null);

        EditText facilityName = dialogView.findViewById(R.id.facility_name);
        EditText facilityLocation = dialogView.findViewById(R.id.facility_location);
        EditText facilityDescription = dialogView.findViewById(R.id.facility_description);

        Button uploadButton = dialogView.findViewById(R.id.facility_image_upload);
        preview = dialogView.findViewById((R.id.facility_image_preview));

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK) ;
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        new AlertDialog.Builder(requireContext())
                .setTitle("Create Facility")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = facilityName.getText().toString().trim();
                    String location = facilityLocation.getText().toString().trim();
                    String description = facilityDescription.getText().toString().trim();

                    if (name.isEmpty() || location.isEmpty()) {
                        Toast.makeText(requireContext(), "Please fill in all required fields!", Toast.LENGTH_SHORT).show();
                    } else {
                        Facility facility = new Facility(deviceId,location,name,description, this.poster_encode);
                        addFacilityToDatabase(facility,button);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    /**
     * Method to handle adding a created facility to the database
     * @param facility
     *      The facility being added
     * @param button
     *      The button pressed to create a facility
     * */
    private void addFacilityToDatabase(Facility facility, Button button) {
        Map<String, Object> facilityDb = new HashMap<>();

        facilityDb.put("organizer_id",facility.getOrganizer_id());
        facilityDb.put("location",facility.getLocation());
        facilityDb.put("name",facility.getName());
        facilityDb.put("description",facility.getDescription());
        facilityDb.put("image", this.poster_encode);

        db.collection("facilities").document(facility.getOrganizer_id()).set(facilityDb)
                .addOnSuccessListener(x -> {
                    checkFacilityStatus(button);
                }).addOnFailureListener(e -> {
                    e.getMessage();
                });

    }

    /**
     * Opens a dialog for user input to create a new event. Captures details such as title, description,
     * date, location, max participants, and an optional event image, and saves the event to Firestore.
     */
    private void getUserInput() {
        // Create input fields for event details
        final EditText titleEditText = new EditText(getContext());
        titleEditText.setHint("Enter Title");

        final EditText descriptionEditText = new EditText(getContext());
        descriptionEditText.setHint("Enter Description");

        // Register Date TextView
        final TextView registerDateTextView = new TextView(getContext());
        registerDateTextView.setText("Select Register Deadline");
        registerDateTextView.setPadding(20, 20, 20, 20);
        registerDateTextView.setTextSize(16);

        final Calendar registerDateCalendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        registerDateTextView.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                registerDateCalendar.set(year, month, dayOfMonth);
                registerDateTextView.setText(dateFormat.format(registerDateCalendar.getTime()));
            },
                    registerDateCalendar.get(Calendar.YEAR),
                    registerDateCalendar.get(Calendar.MONTH),
                    registerDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Event Date TextView
        final TextView eventDateTextView = new TextView(getContext());
        eventDateTextView.setText("Select Event Date");
        eventDateTextView.setPadding(20, 20, 20, 20);
        eventDateTextView.setTextSize(16);
        final Calendar eventDateCalendar = Calendar.getInstance();

        eventDateTextView.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                eventDateCalendar.set(year, month, dayOfMonth);
                eventDateTextView.setText(dateFormat.format(eventDateCalendar.getTime()));
            },
                    eventDateCalendar.get(Calendar.YEAR),
                    eventDateCalendar.get(Calendar.MONTH),
                    eventDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        final EditText maxParticipantsEditText = new EditText(getContext());
        maxParticipantsEditText.setHint("Enter Max Participants");
        maxParticipantsEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        final Button uploadImage = new Button(getContext());
        preview = new ImageView(getContext());

        uploadImage.setText("Upload Event Poster");
        uploadImage.setOnClickListener(v -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        });

        // Create a layout to hold the input fields
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(titleEditText);
        layout.addView(descriptionEditText);
        layout.addView(maxParticipantsEditText);
        layout.addView(registerDateTextView);
        layout.addView(eventDateTextView);
        layout.addView(uploadImage);
        layout.addView(preview);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Event");
        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String registerDateStr = registerDateTextView.getText().toString().trim();
            String eventDateStr = eventDateTextView.getText().toString().trim();
            String maxParticipantsStr = maxParticipantsEditText.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty() || registerDateStr.equals("Select Register Deadline") ||
                    eventDateStr.equals("Select Event Date") || maxParticipantsStr.isEmpty()) {
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

            Date registerDate = registerDateCalendar.getTime();
            Date eventDate = eventDateCalendar.getTime();

            if (registerDate.after(eventDate)) {
                Toast.makeText(getContext(), "Registration deadline must be before the event date.", Toast.LENGTH_SHORT).show();
                return;
            }

            String organizer = Settings.Secure.getString(requireContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            List<String> emptyList = new ArrayList<>();
            String eventId = generateEventId();

            db.collection("facilities").document(deviceId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        String location = (String) documentSnapshot.get("location");

                        // Create a new event and add it to the list
                        Event newEvent = new Event(eventId, title, description, registerDate, eventDate, location, maxParticipants, null, true, this.poster_encode);
                        Map<String, Object> eventDb = new HashMap<>();
                        eventDb.put("name", title);
                        eventDb.put("description", description);
                        eventDb.put("registerDate", registerDate);
                        eventDb.put("eventDate", eventDate);
                        eventDb.put("location", location);
                        eventDb.put("maxParticipants", maxParticipants);
                        eventDb.put("organizer", organizer);
                        eventDb.put("eventId", eventId);
                        eventDb.put("accepted", emptyList);
                        eventDb.put("chosen", emptyList);
                        eventDb.put("declined", emptyList);
                        eventDb.put("waiting", emptyList);
                        eventDb.put("lotteryDrawn", false);
                        eventDb.put("eventPosterEncode", this.poster_encode);
                        eventDb.put("qrHashData", eventId);

                        db.collection("events").document(eventId).set(eventDb);

                        eventList.add(newEvent);
                        arrayAdapter.notifyDataSetChanged();
                    });
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
            } else if (requestCode == QR_SCAN_REQ_CODE) {
                if (data != null && data.getExtras() != null) {
                    String scannedData = data.getStringExtra("SCAN_RESULT");
                    if (scannedData != null) {
                        Toast.makeText(getContext(), "QR Code scanned: " + scannedData, Toast.LENGTH_SHORT).show();
                        openEventDialog(scannedData);
                    } else {
                        Toast.makeText(getContext(), "No data found in the QR code", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "No data received from scanner", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /**
     * Adds an event poster image to a specified event document in Firestore by encoding it in Base64.
     *
     * @param imageBase64 The encoded image as a Base64 string.
     * @param eventId The unique ID of the event to update.
     */
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
     * 
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
                        String event_poster_encoded = (String) doc.get("eventPosterEncode");
                        //more stuff will be added eventually

                        User user = new User(event_organizer, "","","","");


                        Date registerDate = ((com.google.firebase.Timestamp) doc.get("registerDate")).toDate();
                        Date eventDate = ((com.google.firebase.Timestamp) doc.get("eventDate")).toDate();

                        Date currentDate = new Date();

                        if (currentDate.before(registerDate)){
                            Event event = new Event(event_id,event_name, event_desc, registerDate, eventDate, event_location, event_max, user, true, event_poster_encoded);
                            events.add(event);
                        }

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

    /**
     * Open the QR code scanner
     * */
    private void startQrScanner() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        startActivityForResult(intent, 1001);
    }

    /**
     * Open the event dialog based on the scanned QR code
     * @param scannedData
     *      The QR hashed data to find the corresponding event
     * */
    private void openEventDialog(String scannedData) {
        for (Event event : eventList) {
            if (event.getEventId().equals(scannedData)) {
                new EventDialogFragment(event, username)
                        .show(getActivity().getSupportFragmentManager(), "Event");
                return;
            }
        }
        Toast.makeText(getContext(), "Event not found", Toast.LENGTH_SHORT).show();
    }
}