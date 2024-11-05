package com.example.princeproject;

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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    int number = 0;
    private static FirebaseFirestore db;
    private static CollectionReference eventsRef;
    String selected = "";
    List<String> waitingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.next_activity);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EntrantListActivity.class);
                startActivity(intent);
            }
        });

        EditText sampleText = findViewById(R.id.sample_text);
        Button submitButton = findViewById(R.id.submit_button);
        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("events");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = sampleText.getText().toString();
                if (input.isEmpty()) {
                    sampleText.setError("This field is required");
                }
                else {
                    try {
                        number = Integer.parseInt(input);
                        if (number <= 0) {
                            sampleText.setError("Invalid input. Input must be an integer greater than zero");
                        }
                        else {
                            sampleEntrants();
                        }
                    } catch (NumberFormatException e){
                        sampleText.setError("Invalid input. Input must be an integer greater than zero");
                    }
                }

            }
        });



    }
    private void sampleEntrants() {
        TextView sampleOutput = findViewById(R.id.test_sample);
        selected = "";

        eventsRef.whereEqualTo("name", "Test 1").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore", error.toString());
                    return;
                }
                if (value != null) {
                    for (QueryDocumentSnapshot doc : value) {
                        waitingList = (List<String>) doc.get("waiting");
                    }
                    if (waitingList != null) {
                        int waitingNumber = waitingList.size();
                        if (waitingNumber < number) {
                            for (int i = 0; i < waitingNumber; i++) {
                                selected = selected + waitingList.get(i) + " ";
                            }
                        } else {
                            for (int i = 0; i < number; i++) {
                                selected = selected + waitingList.get(i) + " ";
                            }
                        }
                        sampleOutput.setText(selected);
                    }
                }
            }
        });
    }
}