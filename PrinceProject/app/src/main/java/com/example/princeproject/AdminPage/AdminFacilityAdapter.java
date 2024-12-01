package com.example.princeproject.AdminPage;

import android.content.Context;
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

import com.example.princeproject.Facility;
import com.example.princeproject.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Class to display the list of facilities for admins to view
 * */
public class AdminFacilityAdapter extends ArrayAdapter<Facility> {
    private Context context;
    private List<Facility> facilities;
    private FirebaseFirestore db;

    /**
     * Constructor for the facility adapte
     * @param context
     *      The current context of the facility list
     * @param facilities
     *      The list of facilities
     * @param db
     *      The database instance
     * */
    public AdminFacilityAdapter(Context context, List<Facility> facilities, FirebaseFirestore db) {
        super(context,0,facilities);
        this.context = context;
        this.facilities = facilities;
        this.db = db;
    }

    /**
     * Get the view of all facility items for an admin
     * @param position
     *      The position of the selected facility in the event list
     * @param convertView
     *      The view to switch to on selection
     * @param parent
     *      The view of the list of facilities
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_facility_item,parent,false);
        }

        TextView titleTextView = convertView.findViewById(R.id.user_name_text);
        TextView descriptionTextView = convertView.findViewById(R.id.user_role_text);
        TextView locationTextView = convertView.findViewById(R.id.user_email_text);
        Button deleteButton = convertView.findViewById(R.id.delete_button);
        ImageView image = convertView.findViewById(R.id.user_image);
        TextView deleteImage = convertView.findViewById(R.id.remove_picture_text);

        Facility facility = facilities.get(position);

        Uri poster_uri =  facility.decodeBase64String(getContext(),facility.getBase64ImageEncode());

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!(poster_uri == null)) {
                    image.setImageURI(null);
                    image.setImageURI(poster_uri);
                }
            }
        });


        titleTextView.setText(facility.getName());
        descriptionTextView.setText(facility.getDescription());
        locationTextView.setText(facility.getLocation());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFacility(facility.getOrganizer_id(),position);
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeImage(facility.getOrganizer_id(),position);
            }
        });

        return convertView;
    }

    /**
     * Method to remove the image associated to a facility
     * @param organizerId
     *      The ID of the organizer than created the facility
     * @param position
     *      The position of the facility in the list
     * */
    public void removeImage(String organizerId,int position) {
        db.collection("facilities").document(organizerId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String image = documentSnapshot.getString("image");
                    if (image != null) {
                        db.collection("facilities").document(organizerId)
                                .update("image",null)
                                .addOnSuccessListener(x -> {
                                    Facility facility = facilities.get(position);
                                    facility.setBase64ImageEncode(null);

                                    notifyDataSetChanged();

                                    Toast.makeText(context,"Image removed",Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(context,"Cannot remove a default image",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Method to handle the deletion of a facility
     * @param organizerId
     *      The ID of the organizer than created the facility
     * @param position
     *      The position of the facility in the list
     * */
    public void deleteFacility(String organizerId, int position){
        db.collection("facilities").document(organizerId)
                .delete()
                .addOnSuccessListener(x ->{
                   facilities.remove(position);
                   notifyDataSetChanged();
                    Toast.makeText(context,"Facility Deleted",Toast.LENGTH_SHORT).show();
                });
    }
}
