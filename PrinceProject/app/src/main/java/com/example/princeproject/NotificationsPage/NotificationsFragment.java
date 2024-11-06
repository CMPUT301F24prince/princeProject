package com.example.princeproject.NotificationsPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.princeproject.R;

public class NotificationsFragment extends Fragment {

    private NotificationPreferenceManager notificationPreferenceManager;
    private EventManager eventManager;
    private String targetEventId = "1";

    private ListView notification_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //eventManager = new EventManager();
//
        //Button selectEntrantButton = view.findViewById(R.id.selectEntrantButton);
        //selectEntrantButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        // Call the selectRandomEntrant method in EventManager
        //        eventManager.selectRandomEntrant(targetEventId);
        //    }
        //});

        //ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
        //    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        //    return insets;
        //});
    }
}
