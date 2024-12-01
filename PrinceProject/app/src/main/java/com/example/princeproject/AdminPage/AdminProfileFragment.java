package com.example.princeproject.AdminPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.princeproject.R;
import com.example.princeproject.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the dialog fragment for a selected profile
 * */
public class AdminProfileFragment extends Fragment {
    private ListView userListView;
    private List<User> userList;
    private FirebaseFirestore db;
    private AdminProfileAdapter adminProfileAdapter;

    /**
     * Method to handle the creation of the view
     * @param inflater
     *      Inflater to help show the fragment layout
     * @param container
     *      The container for the layout
     * @param savedInstanceState
     *      The current state of the view
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }

    /**
     * Method to invoke actions on creation of the view
     * @param view
     *      The view being shown
     * @param savedInstanceState
     *      The current state of the view
     * */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        userListView = view.findViewById(R.id.user_list_view);
        userList = new ArrayList<>();

        adminProfileAdapter = new AdminProfileAdapter(getContext(),userList,db);
        userListView.setAdapter(adminProfileAdapter);

        adjustListViewPadding();

        getUsers();


    }

    private void adjustListViewPadding() {
        // Get the TabLayout from the activity
        TabLayout tabLayout = requireActivity().findViewById(R.id.navigation_bar);

        // Wait for the TabLayout to be laid out to get its height
        tabLayout.post(() -> {
            int tabHeight = tabLayout.getHeight();

            // Add a small offset to fully show the last item
            int additionalPadding = 90;

            // Adjust the ListView's bottom padding dynamically
            userListView.setPadding(
                    userListView.getPaddingLeft(),
                    userListView.getPaddingTop(),
                    userListView.getPaddingRight(),
                    tabHeight + additionalPadding
            );
        });
    }

    /**
     * Method to get all users from database
     * */
    private void getUsers() {
        db.collection("users").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userList.clear();
                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                        String user_name = (String) doc.get("name");
                        String user_id = (String) doc.get("deviceId");
                        String user_role = (String) doc.get("accountType");
                        String user_email = (String) doc.get("email");
                        String user_phone = (String) doc.get("phone");
                        String image_encode = (String) doc.get("profilePicture");

                        User user = new User(user_name,user_email,user_phone,user_role,user_id,image_encode);
                        userList.add(user);
                    }
                    adminProfileAdapter.notifyDataSetChanged();
                });
    }
}
