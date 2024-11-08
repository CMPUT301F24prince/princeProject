package com.example.princeproject.EventsPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.princeproject.R;
import com.example.princeproject.User;
import com.example.princeproject.WaitingList;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class EventDialogFragment extends DialogFragment {
    interface EventDialogListener{
        //void setEditProfile(User user);
    }

    private Event event;
    private static final String ARG_USER = "user";
    private User user;
    private String username;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EventDialogFragment.EventDialogListener listener;
    private Button joinWaitingListButton;
    private WaitingList waitingList;
    String deviceId;

    /**
     *
     * @param event
     * Event object that fragment will be about
     * @param username
     * Name of user
     */
    public EventDialogFragment(Event event, String username) {
        this.event = event;
        this.username = username;
    }

    @Override
    public void onAttach(@NotNull Context context){
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof EventDialogFragment.EventDialogListener){
            listener = (EventDialogFragment.EventDialogListener) parentFragment;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        deviceId = Settings.Secure.getString(requireContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.event_list_entry_dialog, null);
        waitingList = new WaitingList();
        joinWaitingListButton = view.findViewById(R.id.joinWaitingListButton);

        TextView eventName = view.findViewById(R.id.entry_event_title);
        TextView eventDescription = view.findViewById(R.id.entry_event_description);
        TextView eventLocation = view.findViewById(R.id.entry_event_location);

        eventName.setText(event.getTitle());
        eventDescription.setText(event.getDescription());
        eventLocation.setText(event.getLocation());

        joinWaitingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingList.joinWaitingList(event.getEventId(), deviceId);
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();


        return dialog;
    }

}
