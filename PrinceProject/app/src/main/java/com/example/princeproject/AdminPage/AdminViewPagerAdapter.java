package com.example.princeproject.AdminPage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Class to direct admins to the specific lists of events, facilities, or users
 * */
public class AdminViewPagerAdapter extends FragmentStateAdapter {

    /**
     * Constructor for the adapter
     * @param fragmentActivity
     *      The selected fragment activity to instantiate
     * */
    public AdminViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    /**
     * Create the fragment based on admin selection
     * @param position
     *      The selected position chosen
     * */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminProfileFragment();
            case 1:
                return new AdminEventFragment();
            case 2:
                return new AdminFacilityFragment();
            default:
                return new AdminProfileFragment();
        }
    }

    /**
     * Unit test function to get the item count of buttons
     * */
    @Override
    public int getItemCount() {
        return 3;
    }
}
