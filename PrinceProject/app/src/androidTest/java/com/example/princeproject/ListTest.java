package com.example.princeproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.princeproject.ProfilePage.EntrantListPage.EntrantListActivity;
import com.example.princeproject.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ListTest {

    @Rule
    public ActivityScenarioRule<EntrantListActivity> activityRule =
            new ActivityScenarioRule<>(EntrantListActivity.class);

    @Test
    public void testCancelEntrantRemovesEntrantFromList() {
        // Verify the entrant is present in the list before clicking the cancel button
        onView(withText("User1")).check(matches(withId(R.id.entrant_name)));

        // Click the Cancel button (assuming it's for User1)
        onView(withId(R.id.delete_button)).perform(click());

        // Verify the entrant is no longer in the list after the cancel button is pressed
        onView(withText("User1")).check(matches(withId(R.id.entrant_name)).doesNotExist());
    }
}
