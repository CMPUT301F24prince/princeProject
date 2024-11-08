package com.example.princeproject.NotificationsPage;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.princeproject.R;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_notification, parent, false);
        }

        Notification notification = notifications.get(position);

        TextView notificationTitle = view.findViewById(R.id.notifNameText);
        TextView notificationDetails = view.findViewById(R.id.notifDetailsText);
        TextView notificationLocation = view.findViewById(R.id.notiflocation_text);

        notificationTitle.setText(notification.getName());
        notificationDetails.setText(notification.getDetails());
        notificationLocation.setText(notification.getLocation());

        return view;
    }

    /**
     * Method to show the notification dialog that pops up when a user clicks on the notification. Users
     * can see the name and details of the notification in the dialog, with the option to delete the notification
     * from here.
     * @param position
     *      The position of the notification clicked in the notification array
     * @param notification
     *      The notification object being observed
     * */
    public void showNotificationDialog(Notification notification, int position){
        //Create dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Setting the notification title and details
        builder.setTitle(notification.getName());
        builder.setMessage(notification.getDetails());

        //Setting the buttons
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setNegativeButton("Delete", (dialog, which) -> {
            deleteNotification(notification, position);
            dialog.dismiss();
        });

        //Show dialog
        builder.create().show();
    }

    /**
     * Method to handle the deletion of a notification from the listview.
     * @param position
     *      The position of the notification clicked in the notification array
     * @param notification
     *      The notification to delete
     * */
    private void deleteNotification(Notification notification, int position){
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications")
                .whereEqualTo("deviceId", deviceId)
                .whereEqualTo("title", notification.getName())
                .whereEqualTo("details", notification.getDetails())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            document.getReference().delete();
                            remove(notification);
                            notifyDataSetChanged();
                        }
                    }
                });

    }


}
