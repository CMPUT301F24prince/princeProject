package com.example.princeproject.AdminPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.princeproject.R;
import com.example.princeproject.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class AdminProfileAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> userList;
    private FirebaseFirestore db;


    public AdminProfileAdapter(Context context, List<User> userList, FirebaseFirestore db) {
        super(context,0,userList);
        this.context = context;
        this.userList = userList;
        this.db = db;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.profile_item, parent, false);
        }

        TextView userNameText = convertView.findViewById(R.id.user_name_text);
        TextView userEmailText = convertView.findViewById(R.id.user_email_text);
        TextView userRoleText = convertView.findViewById(R.id.user_role_text);
        Button deleteButton = convertView.findViewById(R.id.delete_button);

        User user = userList.get(position);
        userNameText.setText(user.getName());
        userEmailText.setText(user.getEmail());
        userRoleText.setText(user.getAccount());


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(user.getDeviceId(),position);
            }
        });

        return convertView;
    }

    public void deleteUser(String userId,int position) {
        db.collection("users").document(userId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    userList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context,"User deleted", Toast.LENGTH_SHORT).show();
                });

    }
}
