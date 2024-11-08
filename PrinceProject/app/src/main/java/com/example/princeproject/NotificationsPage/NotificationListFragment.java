package com.example.princeproject.NotificationsPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.princeproject.EventsPage.Event;
import com.example.princeproject.EventsPage.EventDialogFragment;
import com.example.princeproject.R;
import com.example.princeproject.WaitingList;

import org.jetbrains.annotations.NotNull;

public class NotificationListFragment extends DialogFragment {

    interface NotificationDialogListener{
        //void setEditProfile(User user);
    }

    NotificationDialogListener listener;

    private Notification notification;
    private String deviceId;

    NotificationListFragment(Notification noti) {
        this.notification = noti;
    }

    @Override
    public void onAttach(@NotNull Context context){
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof NotificationListFragment.NotificationDialogListener){
            listener = (NotificationListFragment.NotificationDialogListener) parentFragment;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.notification_dialog_fragment, null);
        Button joinWaitingListButton = view.findViewById(R.id.acceptButton);

        WaitingList waitingList = new WaitingList();
        joinWaitingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingList.joinChosenList(notification.EventId, notification.deviceId);
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();


        return dialog;
    }



}
