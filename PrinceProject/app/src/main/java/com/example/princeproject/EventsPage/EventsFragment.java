package com.example.princeproject.EventsPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.princeproject.R;

import java.util.ArrayList;

public class EventsFragment extends Fragment{

    private EventArrayAdapter arrayAdapter;
    private ListView eventFeed;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ArrayList<Event> fillerlist = new ArrayList<Event>();
        fillerlist.add(new Event());
        arrayAdapter = new EventArrayAdapter(view.getContext(), fillerlist);

        eventFeed = (ListView) getView().findViewById(R.id.event_feed);
        eventFeed.setAdapter(arrayAdapter);
    }
}
