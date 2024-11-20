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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminProfileFragment extends Fragment {
    private ListView userListView;
    private List<User> userList;
    private FirebaseFirestore db;
    private AdminProfileAdapter adminProfileAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        userListView = view.findViewById(R.id.user_list_view);
        userList = new ArrayList<>();

        adminProfileAdapter = new AdminProfileAdapter(getContext(),userList,db);
        userListView.setAdapter(adminProfileAdapter);

        getUsers();


    }

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

                        User user = new User(user_name,user_email,user_phone,user_role,user_id);
                        userList.add(user);
                    }
                    adminProfileAdapter.notifyDataSetChanged();
                });
    }
}