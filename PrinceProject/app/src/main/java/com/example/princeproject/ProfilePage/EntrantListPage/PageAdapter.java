package com.example.princeproject.ProfilePage.EntrantListPage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Class to represent a page for organizers to see lists of users
 * related to an event. This includes users that were chosen, have
 * accepted or declined, or waitlisted.
 * */
public class PageAdapter extends FragmentStateAdapter {

    private String eventId;

    /**
     * Constructor for PageAdapter
     *
     * @param fragmentActivity activity for the adapter
     * @param eventId The ID of the current chosen event
     */
    public PageAdapter(@NonNull FragmentActivity fragmentActivity,String eventId) {
        super(fragmentActivity);
        this.eventId = eventId;
    }


    /**
     * Creates and returns the selected fragment
     *
     * @param position the position of the fragment
     * @return A new instance of the selected fragment with the eventId passed through
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new EntrantFragment("chosen").newInstance(eventId);
            case 1:
                return new EntrantFragment("declined").newInstance(eventId);
            case 2:
                return new EntrantFragment("accepted").newInstance(eventId);
            case 3:
                return new EntrantFragment("waiting").newInstance(eventId);
            default:
                return new EntrantFragment("chosen").newInstance(eventId);
        }
    }

    /**
     * Test function to get the count of items on the screen
     * */
    @Override
    public int getItemCount() {
        return 4;
    }


}
