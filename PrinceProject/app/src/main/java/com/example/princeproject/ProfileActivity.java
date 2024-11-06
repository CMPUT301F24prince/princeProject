package com.example.princeproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity implements EditProfileFragment.EditProfileDialogListener{

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private Button editProfile;

    private String deviceId;
    private User currentUser;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        deviceId = getIntent().getStringExtra("deviceId");


        //Set up database
        db = FirebaseFirestore.getInstance();

        //Show the toolbar
        Toolbar toolbar = findViewById(R.id.notificationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");

        //Get the views
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        editProfile = findViewById(R.id.editProfileButton);

        //User currentUser = (User) getIntent().getSerializableExtra("userData");
        getUserInfo();

        editProfile.setOnClickListener(view -> {
            EditProfileFragment fragment = EditProfileFragment.newInstance(currentUser);
            fragment.show(getSupportFragmentManager(), "Edit Profile");
        });
    }

    @Override
    public void setEditProfile(User user){
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());
    }

    private void getUserInfo(){
        Toast.makeText(ProfileActivity.this, "Retrieving data", Toast.LENGTH_SHORT).show();
        db.collection("users")
                .whereEqualTo("deviceId", deviceId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            String userName = document.getString("name");
                            String userEmail = document.getString("email");
                            String userPhone = document.getString("phone");
                            String userAccType = document.getString("accountType");

                            currentUser = new User(userName, userEmail, userPhone, userAccType, deviceId);
                            Toast.makeText(ProfileActivity.this, userName, Toast.LENGTH_SHORT).show();

                            nameTextView.setText(userName);
                            emailTextView.setText(userEmail);
                            phoneTextView.setText(userPhone);
                        }
                    }
                });
    }
}
