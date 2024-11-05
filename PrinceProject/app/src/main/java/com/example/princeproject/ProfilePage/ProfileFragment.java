package com.example.princeproject.ProfilePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.princeproject.ProfilePage.EntrantListPage.EntrantListActivity;
import com.example.princeproject.R;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileFragment extends Fragment {

    TextView myEventsButton;

    int number = 0;
    private FirebaseFirestore db;
    private static CollectionReference eventsRef;
    String selected = "";
    List<String> waitingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        myEventsButton = view.findViewById(R.id.my_events);
        myEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EntrantListActivity.class);
                startActivity(intent);
            }
        });

        //EditText sampleText = view.findViewById(R.id.sample_text);
        //Button submitButton = view.findViewById(R.id.submit_button);
        //db = FirebaseFirestore.getInstance();
        //eventsRef = db.collection("events");
//
        //submitButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        String input = sampleText.getText().toString();
        //        if (input.isEmpty()) {
        //            sampleText.setError("This field is required");
        //        }
        //        else {
        //            try {
        //                number = Integer.parseInt(input);
        //                if (number <= 0) {
        //                    sampleText.setError("Invalid input. Input must be an integer greater than zero");
        //                }
        //                else {
        //                    sampleEntrants(view);
        //                }
        //            } catch (NumberFormatException e){
        //                sampleText.setError("Invalid input. Input must be an integer greater than zero");
        //            }
        //        }
//
        //    }
        //});
    }

    //private void sampleEntrants(View view) {
    //    TextView sampleOutput = view.findViewById(R.id.test_sample);
    //    selected = "";
//
    //    eventsRef.whereEqualTo("name", "Test 1").addSnapshotListener(new EventListener<QuerySnapshot>() {
    //        @Override
    //        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
    //            if (error != null) {
    //                Log.e("Firestore", error.toString());
    //                return;
    //            }
    //            if (value != null) {
    //                for (QueryDocumentSnapshot doc : value) {
    //                    waitingList = (List<String>) doc.get("waiting");
    //                }
    //                if (waitingList != null) {
    //                    int waitingNumber = waitingList.size();
    //                    if (waitingNumber < number) {
    //                        for (int i = 0; i < waitingNumber; i++) {
    //                            selected = selected + waitingList.get(i) + " ";
    //                        }
    //                    } else {
    //                        for (int i = 0; i < number; i++) {
    //                            selected = selected + waitingList.get(i) + " ";
    //                        }
    //                    }
    //                    sampleOutput.setText(selected);
    //                }
    //            }
    //        }
    //    });
    //}
}
