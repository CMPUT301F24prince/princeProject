package com.example.princeproject.AdminPage;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.princeproject.R;
import com.google.android.material.tabs.TabLayout;

/**
 * This is a class that handles when a admin activity for an admin user
 * */
public class AdminActivity extends AppCompatActivity {
    TabLayout navigationBar;
    ViewPager2 rootPager;
    AdminViewPagerAdapter adminViewPagerAdapter;

    /**
     * Method to handle the creation of the activity
     * @param savedInstanceState
     *      The current state of the activity
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        navigationBar = findViewById(R.id.navigation_bar);
        rootPager = findViewById(R.id.root_pager);
        adminViewPagerAdapter = new AdminViewPagerAdapter(this);
        rootPager.setAdapter(adminViewPagerAdapter);

        navigationBar.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                rootPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        rootPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                navigationBar.getTabAt(position).select();
            }
        });
    }
}
