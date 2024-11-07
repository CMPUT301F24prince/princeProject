package com.example.princeproject.ProfilePage;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.princeproject.ProfilePage.EntrantListPage.EntrantListActivity;
import com.example.princeproject.R;
import java.util.ArrayList;
import java.util.List;

import com.example.princeproject.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileFragment extends Fragment implements EditProfileFragment.EditProfileDialogListener {

    View thisview;
    TextView myEventsButton;

    int number = 0;
    private static CollectionReference eventsRef;
    String selected = "";
    List<String> waitingList;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private Button editProfile;

    private String deviceId;
    private User currentUser;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        thisview = view;
        //Set up database
        db = FirebaseFirestore.getInstance();

        deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Show the toolbar
        Toolbar toolbar = view.findViewById(R.id.notificationToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

        //Get the views
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        phoneTextView = view.findViewById(R.id.phoneTextView);
        editProfile = view.findViewById(R.id.editProfileButton);

        getUserInfo();

        editProfile.setOnClickListener(viewArg -> {
            EditProfileFragment fragment = EditProfileFragment.newInstance(currentUser);
            fragment.show(getChildFragmentManager(), "Edit Profile");
        });

        myEventsButton = view.findViewById(R.id.my_events);
        myEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EntrantListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setEditProfile(User user){
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());
    }

    private void getUserInfo(){
        Toast.makeText(thisview.getContext(), "Retrieving data", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(thisview.getContext(), userName, Toast.LENGTH_SHORT).show();

                            nameTextView.setText(userName);
                            emailTextView.setText(userEmail);
                            phoneTextView.setText(userPhone);
                        }
                    }
                });
    }
}