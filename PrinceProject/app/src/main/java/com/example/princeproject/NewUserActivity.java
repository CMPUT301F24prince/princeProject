package com.example.princeproject;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a class that handles when a new user is registering for the app.
 * Users will be prompted to input their information on startup if they have not yet
 * opened the application.
 * */
public class NewUserActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneEditText;
    private Spinner accountTypeDropdown;
    private String userName, email, phone, accType;
    //private ImageView profilePicture;
    private Button confirmButton;
    String deviceId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private User newUser;


    /**
     * Method to initialize the new user view
     * @param savedInstanceState
     *      The current state of the view
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the device ID
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        //Set the view
        setContentView(R.layout.new_user);
        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        phoneEditText = findViewById(R.id.editTextPhone);

        //Initialize the account type dropdown
        accountTypeDropdown = findViewById(R.id.editAccountType);
        ArrayAdapter<CharSequence> accTypeAdapter = ArrayAdapter.createFromResource(this, R.array.accountTypes, android.R.layout.simple_spinner_item);
        accTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeDropdown.setAdapter(accTypeAdapter);

        //Set up the on click listener for the account type dropdown
        accountTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                accType = adapterView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        //Set up the on click listener for the confirm button on the new user view
        //profilePicture = findViewById(R.id.profileImage);
        confirmButton = findViewById(R.id.submitButton);
        confirmButton.setOnClickListener(v -> {
            //Get the details
            userName = nameEditText.getText().toString();
            email = emailEditText.getText().toString();
            phone = phoneEditText.getText().toString();

            //Throw an error if the name or email fields are blank
            if (userName.isEmpty() || email.isEmpty()) {
                if (userName.isEmpty()) {
                    nameEditText.setError("Please enter your full name");
                }

                if (email.isEmpty()) {
                    emailEditText.setError("Please enter your email");
                }
            }
            //Add the new user to the database and create a new user object
            else {
                //Add user to the database
                newUser = new User(userName, email, phone, accType, deviceId);
                Map<String, Object> userDb = new HashMap<>();
                userDb.put("deviceId", deviceId);
                userDb.put("name", userName);
                userDb.put("email", email);
                userDb.put("phone", phone);
                userDb.put("accountType", accType);
                db.collection("users").document(deviceId).set(userDb);
            }
            finish();

        });
    }
}

