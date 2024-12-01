package com.example.princeproject.AdminPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.princeproject.Facility;
import com.example.princeproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the dialog fragment for a selected facility
 * */
public class AdminFacilityFragment extends Fragment {
    private ListView facilityListView;
    private List<Facility> facilities;
    private FirebaseFirestore db;
    private AdminFacilityAdapter adminFacilityAdapter;

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
        return inflater.inflate(R.layout.fragment_admin_facilities, container, false);
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
        facilityListView = view.findViewById(R.id.facility_list_view);
        facilities = new ArrayList<>();

        adminFacilityAdapter = new AdminFacilityAdapter(getContext(),facilities,db);
        facilityListView.setAdapter(adminFacilityAdapter);

        adjustListViewPadding();
        getFacilities();
    }

    /**
     * Method to adjust the padding of the listview to account for the navigation bar
     */
    private void adjustListViewPadding() {
        // Get the TabLayout from the activity
        TabLayout tabLayout = requireActivity().findViewById(R.id.navigation_bar);

        // Wait for the TabLayout to be laid out to get its height
        tabLayout.post(() -> {
            int tabHeight = tabLayout.getHeight();

            // Add a small offset to fully show the last item
            int additionalPadding = 90;

            // Adjust the ListView's bottom padding dynamically
            facilityListView.setPadding(
                    facilityListView.getPaddingLeft(),
                    facilityListView.getPaddingTop(),
                    facilityListView.getPaddingRight(),
                    tabHeight + additionalPadding
            );
        });
    }

    /**
     * Method to get all facilities from database
     * */
    public void getFacilities() {
        db.collection("facilities").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                   facilities.clear();
                   for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                       String organizer_id = (String) doc.get("organizer_id");
                       String facility_desc = (String) doc.get("description");
                       String facility_name = (String) doc.get("name");
                       String facility_location = (String) doc.get("location");
                       String image_encode = (String) doc.get("image");

                       Facility facility = new Facility(organizer_id,facility_location,facility_name,facility_desc,image_encode);
                       facilities.add(facility);
                   }
                   adminFacilityAdapter.notifyDataSetChanged();
                });
    }
}
