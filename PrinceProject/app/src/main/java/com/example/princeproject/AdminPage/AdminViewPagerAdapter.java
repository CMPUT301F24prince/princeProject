package com.example.princeproject.AdminPage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdminViewPagerAdapter extends FragmentStateAdapter {

    public AdminViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminProfileFragment();
            case 1:
                return new AdminEventFragment();
            case 2:
                return new AdminQRCodeFragment();
            case 3:
                return new AdminFacilityFragment();
            case 4:
                return new AdminImageFragment();
            default:
                return new AdminProfileFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
