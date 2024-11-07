package com.example.princeproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.princeproject.EventsPage.EventsFragment;
import com.example.princeproject.ProfilePage.NotificationsFragment;
import com.example.princeproject.ProfilePage.ProfileFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new EventsFragment();
            case 1:
                return new NotificationsFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new EventsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
