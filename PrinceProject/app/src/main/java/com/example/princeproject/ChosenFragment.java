package com.example.princeproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ChosenFragment extends Fragment {

    private static final String EVENT_ID = "event_id";

    public static ChosenFragment newInstance(String eventId) {
        ChosenFragment fragment = new ChosenFragment();
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

        List<String> chosenApplicants = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, chosenApplicants);
        listView.setAdapter(adapter);

        FirestoreQueryHelper.getEntrantListData("chosen",chosenApplicants,adapter,eventId);

        return view;
    }
}
