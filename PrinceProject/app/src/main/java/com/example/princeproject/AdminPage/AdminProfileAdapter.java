package com.example.princeproject.AdminPage;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

/**
 * This is a class that handles the list of profiles for admins to view and modify
 * */
public class AdminProfileAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> userList;
    private FirebaseFirestore db;

    /**
     * Constructor for the profile adapter
     * @param context
     *      The context of the profile list
     * @param userList
     *      The list of users for admins to view
     * @param db
     *      The database instance
     * */
    public AdminProfileAdapter(Context context, List<User> userList, FirebaseFirestore db) {
        super(context,0,userList);
        this.context = context;
        this.userList = userList;
        this.db = db;
    }

    /**
     * Get the view of each user item for an admin
     * @param position
     *      The position of the selected user in the user list
     * @param convertView
     *      The view to switch to on selection
     * @param parent
     *      The view of the list of user
     * */
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
        ImageView image = convertView.findViewById(R.id.user_image);
        TextView removeImage = convertView.findViewById(R.id.remove_picture_text);

        User user = userList.get(position);
        userNameText.setText(user.getName());
        userEmailText.setText(user.getEmail());
        userRoleText.setText(user.getAccount());

        Uri poster_uri =  user.decodeBase64String(getContext());

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!(poster_uri == null)) {
                    image.setImageURI(null);
                    image.setImageURI(poster_uri);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(user.getDeviceId(),position);
            }
        });

        removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removePicture(user.getDeviceId(),position);
            }
        });

        return convertView;
    }

    /**
     * Method to handle the deleting of user profile picture of a selected profile
     * @param userId
     *      The id of the user
     * @param position
     *      The position of the user in the list of users
     * */
    public void removePicture(String userId, int position) {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                   String image = documentSnapshot.getString("profilePicture");
                   boolean defaultImage = Boolean.TRUE.equals(documentSnapshot.getBoolean("default"));
                   if(image != null && !defaultImage) {
                       User user = userList.get(position);
                       user.GenerateProfileImage();

                       db.collection("users").document(userId)
                               .update("profilePicture",user.getProfilePictureEncode())
                               .addOnSuccessListener(x ->{
                                   notifyDataSetChanged();
                                   Toast.makeText(context,"Image removed",Toast.LENGTH_SHORT).show();
                               });

                       db.collection("users").document(userId)
                               .update("defaultImage",true);

                   } else {
                       Toast.makeText(context, "Cannot remove a default image",Toast.LENGTH_SHORT).show();
                   }
                });
    }

    /**
     * Method to handle the deleting of a user's profile
     * @param userId
     *      The id of the user being deleted
     * @param position
     *      The position of the user in the list of users
     * */
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
