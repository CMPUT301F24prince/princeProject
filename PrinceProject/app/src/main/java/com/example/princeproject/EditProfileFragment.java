package com.example.princeproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.content.ContentResolver;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends DialogFragment{

    private static final String ARG_USER = "user";
    private User user;

    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;
    private Spinner accountTypeDropdown;
    private String account;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    interface EditProfileDialogListener{
        void setEditProfile(User user);
    }

    private EditProfileDialogListener listener;

    @Override
    public void onAttach(@NotNull Context context){
        super.onAttach(context);
        if (context instanceof EditProfileDialogListener){
            listener = (EditProfileDialogListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_profile, null);
        editName = view.findViewById(R.id.editTextName);
        editName.setText(user.getName());
        editEmail = view.findViewById(R.id.editTextEmail);
        editEmail.setText(user.getEmail());
        editPhone = view.findViewById(R.id.editTextPhone);
        editPhone.setText(user.getPhone());

        accountTypeDropdown = view.findViewById(R.id.editAccountType);
        ArrayAdapter<CharSequence> accTypeAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.accountTypes, android.R.layout.simple_spinner_item);
        accTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeDropdown.setAdapter(accTypeAdapter);
        int spinnerPosition = accTypeAdapter.getPosition(user.getAccount());
        accountTypeDropdown.setSelection(spinnerPosition);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setView(view)
                .setTitle("Edit Profile Details")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", null);

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button confirm = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            confirm.setOnClickListener(v ->{
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();

                accountTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        account = adapterView.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                });


                if (validateFields()){
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setAccount(account);

                    DocumentReference docRef = db.collection("users").document(user.getDeviceId());
                    docRef.update("name", name);
                    docRef.update("email", email);
                    docRef.update("phone", phone);
                    docRef.update("accountType", account);
                }
                listener.setEditProfile(user);
                dialog.dismiss();
            });
        });

        return dialog;
    }

    private boolean validateFields(){
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        //String phone = editPhone.getText().toString();

        boolean valid = true;

        if (name.isEmpty()){
            editName.setError("Please enter a valid name");
            valid = false;
        }

        if (email.isEmpty()){
            editName.setError("Please enter a valid email");
            valid = false;
        }

        return valid;
    }

    public static EditProfileFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable("user",user);
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }




}
