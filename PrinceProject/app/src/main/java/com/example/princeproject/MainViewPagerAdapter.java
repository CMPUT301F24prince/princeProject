package com.example.princeproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.princeproject.EventsPage.EventsFragment;
import com.example.princeproject.NotificationsPage.NotificationsFragment;
import com.example.princeproject.ProfilePage.ProfileFragment;

/**
 * Class to handle the page management for the application, based on the selection
 * on the taskbard.
 */
public class MainViewPagerAdapter extends FragmentStateAdapter {
    /**
     * Constructor for the adapter
     * @param fragmentActivity fragmentActivity reference
     */
    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Showing fragments based on user navigation of the app
     * @param position
     *     The position of the button being clicked on the taskbar
     */
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

    /**
     * Unit test function to ensure all button are present on the taskbar
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}
