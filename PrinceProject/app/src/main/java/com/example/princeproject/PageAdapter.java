package com.example.princeproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {

    private String eventId;

    public PageAdapter(@NonNull FragmentActivity fragmentActivity,String eventId) {
        super(fragmentActivity);
        this.eventId = eventId;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ChosenFragment().newInstance(eventId);
            case 1:
                return new CanceledFragment().newInstance(eventId);
            case 2:
                return new EnrolledFragment().newInstance(eventId);
            default:
                return new EnrolledFragment().newInstance(eventId);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
