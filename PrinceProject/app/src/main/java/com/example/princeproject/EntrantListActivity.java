package com.example.princeproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class EntrantListActivity extends AppCompatActivity {
    private Spinner eventSelection;
    private List<String> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrant_list);

        eventSelection = findViewById(R.id.event_spinner);

        // gets the list of events for the current organizer
        FirestoreQueryHelper.getOrganizerEvents(this,"kyle",events,eventSelection);

        eventSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // get the selected event ID and update the fragment with it
                String eventId = events.get(i);
                updateFragment(eventId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // if nothing is selected choose the event at index 0 to display at first
                String eventId = events.get(0);
                updateFragment(eventId);
            }
        });


    }

    /**
     * This function updates every fragment, setting up the display and passing through the eventId.
     * @param eventId The current eventId relating to the current event selected in the spinner
     */
    private void updateFragment(String eventId) {
        PageAdapter adapter = new PageAdapter(this, eventId);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        // set up the tab layouts
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Chosen");
                    break;
                case 1:
                    tab.setText("Declined");
                    break;
                case 2:
                    tab.setText("Accepted");
                    break;
            }
        }).attach();
    }
}
