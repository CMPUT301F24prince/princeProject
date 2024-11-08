package com.example.princeproject.EventsPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.princeproject.Event;
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
    //username will be removed once we implement User
    private String username;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EventDialogFragment.EventDialogListener listener;
    private Button joinWaitingListButton;
    private WaitingList waitingList;

    public EventDialogFragment(Event event, String username) {
        //I'm gonna change this to take in a user object instead of just the username eventually
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.event_list_entry_dialog, null);
        waitingList = new WaitingList();
        joinWaitingListButton = view.findViewById(R.id.joinWaitingListButton);

        joinWaitingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //These values shouldn't be hard coded, but they are until other things are completed
                waitingList.joinWaitingList(event.getTitle(), username);
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();


        return dialog;
    }

}
