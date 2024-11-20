package com.example.princeproject.ProfilePage;


import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.LayoutInflater;

import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.princeproject.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import com.example.princeproject.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * This is a class that implements the Edit Profile functinality.
 * When a user wants to edit their details, a dialog will pop up for users
 * to change the details.
 * */
public class EditProfileFragment extends DialogFragment{

    private static final String ARG_USER = "user";
    private User user;

    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;
    private Spinner accountTypeDropdown;
    private String account;
    private ImageView profile_preview;
    private Button uploadButton;
    private final int GALLERY_REQ_CODE = 1000;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    interface EditProfileDialogListener{
        void setEditProfile(User user);
    }

    private EditProfileDialogListener listener;

    /**
     * Method to attach the dialog to the user profile
     * @param context
     *      The current context of the profile
     * */
    @Override
    public void onAttach(@NotNull Context context){
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof EditProfileDialogListener){
            listener = (EditProfileDialogListener) parentFragment;
        }
    }

    /**
     * Method to handle the initialization of the dialog by taking in the passed User object
     * @param savedInstanceState
     *      The current state of the profile view
     * */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    /**
     * Method to handle the creation of the dialog to edit the profile
     * @param savedInstanceState
     *      The current state of the profile view
     * */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        //Initialize the views and the edit text fields
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_profile, null);
        editName = view.findViewById(R.id.editTextName);
        editName.setText(user.getName());
        editEmail = view.findViewById(R.id.editTextEmail);
        editEmail.setText(user.getEmail());
        editPhone = view.findViewById(R.id.editTextPhone);
        editPhone.setText(user.getPhone());

        //Initialize the dropdown to choose account type
        accountTypeDropdown = view.findViewById(R.id.editAccountType);
        ArrayAdapter<CharSequence> accTypeAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.accountTypes, android.R.layout.simple_spinner_item);
        accTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeDropdown.setAdapter(accTypeAdapter);
        int spinnerPosition = accTypeAdapter.getPosition(user.getAccount());
        accountTypeDropdown.setSelection(spinnerPosition);

        //Builder to build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setView(view)
                .setTitle("Edit Profile Details")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", null);

        this.uploadButton = view.findViewById(R.id.profile_image_upload);
        this.profile_preview = view.findViewById((R.id.profile_image_preview));

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {

            uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK) ;
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, GALLERY_REQ_CODE);
                }
            });

            Button confirm = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            confirm.setOnClickListener(v ->{
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();

                //Setting the listener to the account type dropdown
                accountTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        account = adapterView.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                });

                //Check if fields are valid, then set the changed user info in the database
                if (validateFields()){
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setAccount(accountTypeDropdown.getSelectedItem().toString());

                    DocumentReference docRef = db.collection("users").document(user.getDeviceId());
                    docRef.update("name", name);
                    docRef.update("email", email);
                    docRef.update("phone", phone);
                    docRef.update("accountType", accountTypeDropdown.getSelectedItem().toString());
                    docRef.update("profilePicture", user.getProfilePictureEncode());
                }
                //Notify listener that profile has been updated, and to update info on main activity
                listener.setEditProfile(user);
                dialog.dismiss();
            });
        });


        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode==GALLERY_REQ_CODE) {
                android.net.Uri imageUri = data.getData();
                profile_preview.setImageURI(imageUri);

                InputStream imageStream = null;
                try {
                    imageStream = getContext().getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                }
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                String base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
                this.user.setProfilePictureEncode(base64String);
            }


        }
    }

    /**
     * Method to validate that the name and email fields are inputted
     * @return
     *      True if the name and email fields are filled, false otherwise
     * */
    private boolean validateFields(){
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        //String phone = editPhone.getText().toString();

        boolean valid = true;

        //Set error if name is empty
        if (name.isEmpty()){
            editName.setError("Please enter a valid name");
            valid = false;
        }

        //Set error if email is empty
        if (email.isEmpty()){
            editName.setError("Please enter a valid email");
            valid = false;
        }

        return valid;
    }

    /**
     * Method to get the passed User object from ProfileActivity
     * @param user
     *      The user object being passed to the fragment
     * @return
     *      True if the name and email fields are filled, false otherwise
     * */
    public static EditProfileFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable("user",user);
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }




}
