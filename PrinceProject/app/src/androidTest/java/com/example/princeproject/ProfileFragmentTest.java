package com.example.princeproject;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.princeproject.ProfilePage.EntrantListPage.EntrantListActivity;
import com.example.princeproject.ProfilePage.ProfileFragment;
import com.example.princeproject.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileFragmentTest {

    @Before
    public void setUp() {
        // Initialize Intents before starting the test
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Intents after the test is completed
        Intents.release();
    }

    @Test
    public void testMyEventsButtonLaunchesEntrantListActivity() {
        // Launch ProfileFragment in isolation
        FragmentScenario.launchInContainer(ProfileFragment.class);

        // Click the myEventsButton
        onView(withId(R.id.my_events)).perform(click());

        // Verify that the Intent was started for EntrantListActivity
        intended(hasComponent(EntrantListActivity.class.getName()));
    }
}

