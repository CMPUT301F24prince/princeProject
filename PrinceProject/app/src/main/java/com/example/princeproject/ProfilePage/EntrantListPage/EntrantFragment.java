package com.example.princeproject.ProfilePage.EntrantListPage;
import com.example.princeproject.EventsPage.Event;
import com.example.princeproject.R;
import com.example.princeproject.NotificationsPage.EventManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.util.TypedValue;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntrantFragment extends Fragment {
    private static String type;

    private static final String EVENT_ID = "event_id";
    private ArrayList<String> selected_users = new ArrayList<>();

    /**
     * Creates a new instance of EntrantFragment with the eventId passed into it
     * @param eventId the id of the event currently selected
     * @return returns a new instance of the fragment with the eventID in its arguments
     */
    public static EntrantFragment newInstance(String eventId) {
        EntrantFragment fragment = new EntrantFragment(type);
        Bundle args = new Bundle();
        args.putString(EVENT_ID,eventId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Constructor to create a new fragment with the designated type of information
     * @param type type of information (chosen, declined, accepted)
     */
    public EntrantFragment(String type) {
        this.type = type;
    }

    /**
     * Method to handle the creation of the view
     * @param inflater
     *      Inflater to help show the fragment layout
     * @param container
     *      The container for the layout
     * @param savedInstanceState
     *      The current state of the view
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = view.findViewById(R.id.list_view);
        String eventId = getArguments().getString(EVENT_ID);
        Button selectAllButton = view.findViewById(R.id.select_all_button);
        Button inviteButton = view.findViewById(R.id.invite_button);


        List<String> chosenApplicants = new ArrayList<>();
        EntrantAdapter adapter = new EntrantAdapter(getContext(),chosenApplicants,type,eventId);
        listView.setAdapter(adapter);

        FirestoreQueryHelper.getEntrantListData(type,chosenApplicants,adapter,eventId);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long i){
                Object item = adapter.getItemAtPosition(position);
                String user_device = (String) item;

                selectListViewItem(v, user_device);
            }
        });

        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String s: selected_users) {

                    getCustomNotificationData(eventId);
                    //EventManager.selectEntrantByDeviceId(getContext(),eventId, s);
                }
            }
        });

        selectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_users = new ArrayList<>();
                for (int i = 0; i < listView.getCount(); i++) {
                    String user_device = (String) listView.getItemAtPosition(i);

                    selectListViewItem(listView.getChildAt(i - listView.getFirstVisiblePosition()), user_device);
                }
            }
        });

        return view;
    }


    private void selectListViewItem(View v, String user_device) {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.windowBackground, typedValue, true);
        int default_color = typedValue.data;

        if (selected_users.contains(user_device)) {
            selected_users.remove(user_device);
            v.setBackgroundColor(default_color);
        }
        else {
            selected_users.add(user_device);
            v.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.light_violet));
        }
    }

    private void getCustomNotificationData(String eventId) {
        final EditText notificationDetails = new EditText(getContext());
        notificationDetails.setHint("message");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(notificationDetails);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Send Notification");
        builder.setView(layout);

        builder.setPositiveButton("Send", (dialog, which) -> {
            String description = notificationDetails.getText().toString().trim();

            if (description.isEmpty()) {
                Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            for (String s: selected_users) {
                EventManager.sendCustomNotification(s, eventId, description);
            }
        });
        builder.setNegativeButton("Cancel", ((dialog, which) -> dialog.cancel()));
        builder.show();
    }
}
