package com.example.princeproject;

import android.app.Activity;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {


    //Declaring firestore instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef;
    private String deviceId;
    private User currentUser;

    private Button notifButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Is this needed?
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        //    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        //    return insets;
        //});

        //Get the device id
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        notifButton = findViewById(R.id.notifButton);
        profileButton = findViewById(R.id.profileButton);

        //Check database for existing
        checkUser();

        //Intent for notifications tab
        notifButton.setOnClickListener(n ->{
            Intent intent = new Intent (MainActivity.this, NotificationActivity.class);
            //Pass the device id
            intent.putExtra("deviceId", deviceId);
            startActivity(intent);
        });

        //Intent for profile tab
        profileButton.setOnClickListener(p ->{
           Intent intent = new Intent (MainActivity.this, ProfileActivity.class);
           //Pass the device id
            intent.putExtra("deviceId", deviceId);
            startActivity(intent);
        });

    }

    private void checkUser(){
        //Check database for existing
        db.collection("users")
                //Check if device id is in database
                .document(deviceId)
                .get()
                .addOnSuccessListener(document -> {
                    //If device is already enrolled, do nothing
                    if (document.exists()) {
                        //User already exists
                        Toast.makeText(MainActivity.this, "Device already enrolled", Toast.LENGTH_SHORT).show();
                        currentUser = new User(document.getString("name"),document.getString("email"),document.getString("phone"),document.getString("accountType"), deviceId);

                    }
                    //Else, open the new user page for users to fill in their details
                    else {
                        //Go to new user
                        Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                        newUserLauncher.launch(intent);
                    }
                });
    }

    private final ActivityResultLauncher<Intent> newUserLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                    currentUser = (User) result.getData().getSerializableExtra("user");
                }
            }
    );

}