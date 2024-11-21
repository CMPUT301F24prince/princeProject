package com.example.princeproject.ProfilePage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.example.princeproject.EventsPage.Event;
import com.example.princeproject.EventsPage.EventArrayAdapter;
import com.example.princeproject.ProfilePage.EntrantListPage.EntrantListActivity;
import com.example.princeproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.princeproject.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * This is a class that handles the profile page for the user, where they can view their
 * inputted details.
 * */
public class ProfileFragment extends Fragment implements EditProfileFragment.EditProfileDialogListener {

    View thisview;
    TextView myEventsButton;

    int number = 0;
    private static CollectionReference eventsRef;
    String selected = "";
    List<String> waitingList;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView accountTextView;
    private Button editProfile;

    private String deviceId;
    private User currentUser;
    private FirebaseFirestore db;

    private ArrayList<Event> organizedEvents;
    private ListView organizedEventsListView;
    private EventArrayAdapter arrayAdapter;

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

        thisview = view;
        //Set up database
        db = FirebaseFirestore.getInstance();

        deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Show the toolbar
        Toolbar toolbar = view.findViewById(R.id.notificationToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

        //Get the views
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        phoneTextView = view.findViewById(R.id.phoneTextView);
        accountTextView = view.findViewById(R.id.accountTextView);
        editProfile = view.findViewById(R.id.editProfileButton);

        getUserInfo();

        editProfile.setOnClickListener(viewArg -> {
            EditProfileFragment fragment = EditProfileFragment.newInstance(currentUser);
            fragment.show(getChildFragmentManager(), "Edit Profile");
        });

        myEventsButton = view.findViewById(R.id.my_events);
        organizedEventsListView = view.findViewById(R.id.organized_events_listview);

        organizedEvents = new ArrayList<>();
        arrayAdapter = new EventArrayAdapter(view.getContext(), organizedEvents);
        organizedEventsListView.setAdapter(arrayAdapter);

        db.collection("events").whereEqualTo("organizer",deviceId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                   if (!queryDocumentSnapshots.isEmpty()) {
                       myEventsButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent = new Intent(view.getContext(), EntrantListActivity.class);
                               startActivity(intent);
                           }
                       });
                   } else {
                       myEventsButton.setVisibility(View.INVISIBLE);
                   }
                });

        organizedEventsListView.setOnItemClickListener((parent, v, position, id) -> {
            Event event = organizedEvents.get(position);
            showPopupMenu(v, event);
        });

        loadOrganizedEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadOrganizedEvents();  // Reload events whenever the fragment is resumed
    }

    /**
     * Method to edit the profile of the user when a user chooses to
     * @param user
     *      The user object being modified
     * */
    @Override
    public void setEditProfile(User user){
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());
        accountTextView.setText(user.getAccount());
    }

    /**
     * Method to retrieve user information from the database via the device ID
     * */
    private void getUserInfo(){
        Toast.makeText(thisview.getContext(), "Retrieving data", Toast.LENGTH_SHORT).show();
        db.collection("users")
                .whereEqualTo("deviceId", deviceId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            String userName = document.getString("name");
                            String userEmail = document.getString("email");
                            String userPhone = document.getString("phone");
                            String userAccType = document.getString("accountType");

                            currentUser = new User(userName, userEmail, userPhone, userAccType, deviceId);
                            Toast.makeText(thisview.getContext(), userName, Toast.LENGTH_SHORT).show();

                            nameTextView.setText(userName);
                            emailTextView.setText(userEmail);
                            phoneTextView.setText(userPhone);
                            accountTextView.setText(userAccType);
                        }
                    }
                });
    }

    /**
     * Method to load and display events organized by the current user
     */
    private void loadOrganizedEvents() {
        organizedEvents.clear(); // Clear any previous events to reload
        db.collection("events")
                .whereEqualTo("organizer", deviceId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String eventId = document.getString("eventId");
                            String title = document.getString("name");
                            String description = document.getString("description");
                            Date startDate = document.getDate("startDate");
                            Date endDate = document.getDate("endDate");
                            String location = document.getString("location");
                            int maxParticipants = document.getLong("maxParticipants").intValue();
                            String eventPosterEncode = document.getString("eventPosterEncode");

                            Event event = new Event(eventId, title, description, startDate, endDate, location, maxParticipants, null, true, eventPosterEncode);
                            organizedEvents.add(event);
                        }

                        arrayAdapter.notifyDataSetChanged(); // Update the adapter after loading data
                    } else {
                        Toast.makeText(getContext(), "No events found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error fetching events: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Method that displays a pop up menu. Called when an event is pressed
     *
     * @param view
     * @param event
     */
    private void showPopupMenu(View view, Event event) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

        // Show the menu
        popupMenu.show();

        // Set click listener for the "Generate QR Code" button in the menu
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.generate_qr_code) {
                generateQRCode(event.getEventId());  // Generate QR code for the event
                return true;
            }
            return false;
        });
    }

    /**
     * Method that generates a qrcode from an events eventId
     *
     * @param eventId
     */
    private void generateQRCode(String eventId) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            // Generate QR Code from event ID
            BitMatrix bitMatrix = writer.encode(eventId, BarcodeFormat.QR_CODE, 512, 512);

            // Create a Bitmap from the BitMatrix
            Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565);
            for (int x = 0; x < 512; x++) {
                for (int y = 0; y < 512; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            // Show the QR code in a dialog
            showQRCodeDialog(bitmap, eventId);

        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error generating QR code", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method shows a generated QRcode
     *
     * @param qrCodeBitmap
     */
    private void showQRCodeDialog(Bitmap qrCodeBitmap, String eventId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("QR Code")
                .setMessage("Scan this QR code to access the event.")
                .setCancelable(true)
                .setPositiveButton("Close", (dialog, which) -> dialog.dismiss())
                .setNeutralButton("View Hash Data", (dialog, which) -> {
                    // Display the hash data in a new dialog
                    new AlertDialog.Builder(getContext())
                            .setTitle("QR Code Hash Data")
                            .setMessage("Event ID: " + eventId)  // Display the hash or encoded data here
                            .setPositiveButton("Close", null)
                            .show();
                });

        // Set the image view to display the QR code
        ImageView qrImageView = new ImageView(getContext());
        qrImageView.setImageBitmap(qrCodeBitmap);
        builder.setView(qrImageView);

        builder.create().show();
    }
}
