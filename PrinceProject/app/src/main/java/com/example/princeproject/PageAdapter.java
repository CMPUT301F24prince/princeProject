package com.example.princeproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {

    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ChosenFragment();
            case 1:
                return new CanceledFragment();
            case 2:
                return new EnrolledFragment();
            default:
                return new EnrolledFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
