package com.example.princeproject.NotificationsPage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.princeproject.EventsPage.Event;
import com.example.princeproject.R;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an array adapter class for Notification objects. Handles the functionality
 * of the notification listview and individual notification objects.
 * */
public class NotificationArrayAdapter extends ArrayAdapter<Notification> {

    private ArrayList<Notification> notifications;
    private Context context;
    private NotificationPreferenceManager notificationPreferenceManager = new NotificationPreferenceManager();

    /**
     * Constructor for the NotificationArrayAdapter class
     * @param context
     *      The current context
     * @param notifications
     *      An array of notifications
     * */
    public NotificationArrayAdapter(Context context, ArrayList<Notification> notifications){
        super(context, 0 , notifications);
        this.notifications = notifications;
        this.context = context;
    }

    /**
     * Method to initialize the view of a notification object, and handling events when a
     * notification is clicked
     * @param position
     *      The position of the notification clicked in the notification array
     * @param convertView
     *      The view of the notification object
     * @param parent
     *      The list view of notifications
     * @return
     *      The view of the notification object
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_notification, parent, false);
        }

        Notification notification = notifications.get(position);

        TextView notificationTitle = view.findViewById(R.id.notifNameText);
        TextView notificationDetails = view.findViewById(R.id.notifDetailsText);
        TextView notificationLocation = view.findViewById(R.id.notiflocation_text);
        ImageView notificationImage = view.findViewById(R.id.notif_event_image);

        notificationTitle.setText(notification.getName());
        notificationDetails.setText(notification.getDetails());
        notificationLocation.setText(notification.getLocation());

        Button acceptButton = view.findViewById(R.id.acceptButton);
        Button declineButton = view.findViewById(R.id.declineButton);

        setEventImage(notificationImage, notification.getEventId());

        if("Sorry!".equals(notification.getName())) {
            acceptButton.setVisibility(View.INVISIBLE);

            declineButton.setText("Dismiss");
            declineButton.setOnClickListener(v -> deleteAllNotifications(notification));
        } else {
            acceptButton.setVisibility(View.VISIBLE);
            declineButton.setText("Decline");

            acceptButton.setOnClickListener(v -> acceptInvitation(notification,position));
            declineButton.setOnClickListener(v -> declineInvitation(notification, position));
        }


        return view;
    }

    /**
     * Method to set the image for an event
     * @param image
     *      The image for the event
     * @param eventId
     *      The id of the event
     * */
    private void setEventImage(ImageView image,String eventId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String image_encode = documentSnapshot.getString("eventPosterEncode");
                    //Event event = new Event(image_encode,eventId);

                    if (image_encode != null && !image_encode.isEmpty()) {
                        // Decode the Base64 string into a Bitmap
                        byte[] decodedBytes = Base64.decode(image_encode, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                        // Display the decoded image in the ImageView
                        image.setImageBitmap(decodedBitmap);
                    }
                });
    }

    /**
     * Logic for declining a notificiation for event registration
     * @param notification
     *      The notification object
     * @param position
     *      The position of the notification clicked in the notification array
     * */
    private void declineInvitation(Notification notification, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Directly reference the event document using the eventId as the document name
        db.collection("events").document(notification.getEventId())
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        // Get the current lists for chosen and declined
                        List<String> chosenList = (List<String>) document.get("chosen");
                        List<String> declinedList = (List<String>) document.get("declined");

                        // Check if the user is in the chosen list
                        if (chosenList != null && chosenList.contains(notification.getDeviceId())) {
                            // Remove from chosen list
                            chosenList.remove(notification.getDeviceId());

                            // Add to declined list
                            if (declinedList == null) {
                                declinedList = new ArrayList<>();
                            }
                            declinedList.add(notification.getDeviceId());

                            // Update Firestore
                            document.getReference().update(
                                    "chosen", chosenList,
                                    "declined", declinedList
                            ).addOnSuccessListener(aVoid -> {
                                // Delete the notification from Firestore and remove from the UI
                                deleteNotification(notification);

                                Toast.makeText(context, "Invitation declined.", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(context, "Failed to decline invitation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            Toast.makeText(context, "User not found in chosen list.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Event not found.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Error fetching event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Logic for accepting a notificiation for event registration
     * @param notification
     *      The notification object
     * @param position
     *      The position of the notification clicked in the notification array
     * */
    private void acceptInvitation(Notification notification, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Directly reference the event document using the eventId as the document name
        db.collection("events").document(notification.getEventId())
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        // Get the current lists for chosen and accepted
                        List<String> chosenList = (List<String>) document.get("chosen");
                        List<String> acceptedList = (List<String>) document.get("accepted");

                        // Check if the user is in the chosen list
                        if (chosenList != null && chosenList.contains(notification.getDeviceId())) {
                            // Remove from chosen list
                            chosenList.remove(notification.getDeviceId());

                            // Add to accepted list
                            if (acceptedList == null) {
                                acceptedList = new ArrayList<>();
                            }
                            acceptedList.add(notification.getDeviceId());

                            // Update Firestore
                            document.getReference().update(
                                    "chosen", chosenList,
                                    "accepted", acceptedList
                            ).addOnSuccessListener(aVoid -> {
                                deleteNotification(notification);
                                Toast.makeText(context, "Invitation accepted.", Toast.LENGTH_SHORT).show();

                            }).addOnFailureListener(e -> {
                                Toast.makeText(context, "Failed to accept invitation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            Toast.makeText(context, "User not found in chosen list.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Event not found.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Error fetching event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Deleting all notifications sent from a particular event
     * @param notification
     *      The notification object
     * */
    private void deleteAllNotifications(Notification notification) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Query to find other notifications with the same eventId and userId
        db.collection("notifications")
                .whereEqualTo("eventId", notification.getEventId())
                .whereEqualTo("userId", notification.getDeviceId())
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        // Delete each matching notification
                        document.getReference().delete()
                                .addOnSuccessListener(aVoid -> {
                                    // Remove from the local list and update UI
                                    notifications.removeIf(n -> n.getId().equals(document.getId()));
                                    notifyDataSetChanged();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(context, "Error deleting notification: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error fetching notifications: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Method for deleting a notification on a particular users screen
     * @param notification
     *      The notification object
     * */
    private void deleteNotification(Notification notification) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications")
                .document(notification.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Remove notification from the UI
                    notifications.remove(notification);
                    notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error deleting notification: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }







}
