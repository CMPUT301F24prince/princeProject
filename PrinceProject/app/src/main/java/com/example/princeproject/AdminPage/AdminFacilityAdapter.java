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

import com.example.princeproject.EventsPage.Event;
import com.example.princeproject.Facility;
import com.example.princeproject.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdminFacilityAdapter extends ArrayAdapter<Facility> {
    private Context context;
    private List<Facility> facilities;
    private FirebaseFirestore db;

    public AdminFacilityAdapter(Context context, List<Facility> facilities, FirebaseFirestore db) {
        super(context,0,facilities);
        this.context = context;
        this.facilities = facilities;
        this.db = db;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_facility_item,parent,false);
        }

        TextView titleTextView = convertView.findViewById(R.id.title_text);
        TextView descriptionTextView = convertView.findViewById(R.id.description_text);
        TextView locationTextView = convertView.findViewById(R.id.location_text);
        Button deleteButton = convertView.findViewById(R.id.delete_button_facility);

        Facility facility = facilities.get(position);
        titleTextView.setText(facility.getName());
        descriptionTextView.setText(facility.getDescription());
        locationTextView.setText(facility.getLocation());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFacility(facility.getOrganizer_id(),position);
            }
        });

        return convertView;
    }

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
