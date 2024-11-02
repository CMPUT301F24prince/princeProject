package com.example.princeproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CanceledFragment extends Fragment {

    private static final String EVENT_ID = "event_id";

    /**
     * Creates a new instance of CanceledFragment with the eventId passed into it
     * @param eventId the id of the event currently selected
     * @return returns a new instance of the fragment with the eventID in its arguments
     */
    public static CanceledFragment newInstance(String eventId) {
        CanceledFragment fragment = new CanceledFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_ID,eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = view.findViewById(R.id.list_view);
        String eventId = getArguments().getString(EVENT_ID);

        List<String> canceledApplicants = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, canceledApplicants);
        listView.setAdapter(adapter);

        FirestoreQueryHelper.getEntrantListData("declined", canceledApplicants,adapter,eventId);

        return view;
    }
}
