package com.example.princeproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.princeproject.AdminPage.AdminActivity;
import com.example.princeproject.NotificationsPage.Notification;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

/**
 * Main activity class for the app
 * */
public class MainActivity extends AppCompatActivity {
    //UI properties
    TabLayout mainNavigationBar;
    ViewPager2 rootPager;
    MainViewPagerAdapter mainViewPagerAdapter;

    //Database properties
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //User properties
    private String deviceId;
    private User currentUser;
    private Handler handler = new Handler();
    private Runnable runnable;


    /**
     * Method to initialize the main activity view
     * @param savedInstanceState
     *      The current state of the view
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        checkUser();

        mainNavigationBar = findViewById(R.id.main_navigation_bar);
        rootPager = findViewById(R.id.root_pager);
        mainViewPagerAdapter = new MainViewPagerAdapter(this);
        rootPager.setAdapter(mainViewPagerAdapter);

        mainNavigationBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                rootPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rootPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mainNavigationBar.getTabAt(position).select();
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                getNotifications();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    /**
     * Method to check if the device/user exists in the database
     * */
    public void checkUser(){
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

    /**
     * If the deviceId is not in the database, the user is sent to form to create an account
     */
    private final ActivityResultLauncher<Intent> newUserLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                    currentUser = (User) result.getData().getSerializableExtra("user");
                }
            }
    );

    /**
     * Method to retrieve notifications tied to a user
     */
    private void getNotifications() {
        db.collection("notifications")
                .whereEqualTo("userId", deviceId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.e("Notifications", "Listen failed: ", e);
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String id = document.getId();
                            String title = document.getString("title");
                            String details = document.getString("details");
                            String location = document.getString("location");
                            String eventId = document.getString("eventId");
                            String userId = document.getString("userId");
                            Boolean received = document.getBoolean("received");

                            Notification notification = new Notification(id,title, details, location, userId, eventId);
                            if (Boolean.FALSE.equals(received)) {
                                notification.sendAndroidNotification(this);
                            }
                        }
                    }
                });
    }
}