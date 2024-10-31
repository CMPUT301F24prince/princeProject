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

import java.util.Arrays;
import java.util.List;

public class EnrolledFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = view.findViewById(R.id.list_view);

        // Sample data
        List<String> enrolledEntrants = Arrays.asList("Entrant 7", "Entrant 8", "Entrant 9");

        // Create an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, enrolledEntrants);

        // Set adapter to the ListView
        listView.setAdapter(adapter);

        return view;
    }
}
