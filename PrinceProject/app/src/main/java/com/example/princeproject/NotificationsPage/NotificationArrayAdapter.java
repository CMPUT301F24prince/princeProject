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

public class NotificationArrayAdapter extends ArrayAdapter<Notification> {

    private ArrayList<Notification> notifications;
    private Context context;
    private NotificationPreferenceManager notificationPreferenceManager = new NotificationPreferenceManager();

    public NotificationArrayAdapter(Context context, ArrayList<Notification> notifications){
        super(context, 0 , notifications);
        this.notifications = notifications;
        this.context = context;
    }

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

        view.setOnClickListener(v -> showNotificationDialog(notification, position));

        Button optOutButton = view.findViewById(R.id.optOutButton);
        optOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with actual user ID from Firestore
                String userId = "9620cce4e7c896f8";

                // Set Allow Notification to false
                notificationPreferenceManager.setNotificationPreference(userId, false);
            }
        });

        return view;
    }

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
