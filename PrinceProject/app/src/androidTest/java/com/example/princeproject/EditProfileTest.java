package com.example.princeproject;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

/**
 * UI test case for user story US 01.02.02
 * */
public class EditProfileTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testEditUserProfile() throws InterruptedException {
        //Navigate to Profile to launch profile fragment
        Espresso.onView(withText("My Profile")).perform(click());
        //Wait for edit profile button to come up
        Espresso.onView(withText("Edit Profile")).check(matches(isDisplayed()));
        //Sleep for 4 seconds
        Thread.sleep(4000);
        //Click edit profile
        Espresso.onView(ViewMatchers.withId(R.id.editProfileButton)).perform(click());
        //Wait for edit name to appear
        Espresso.onView(ViewMatchers.withId(R.id.editTextName)).check(matches(isDisplayed()));
        //Add text to edit name
        Espresso.onView(ViewMatchers.withId(R.id.editTextName)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editTextName)).perform(ViewActions.typeText("Testing"));
        //Confirm changes
        Espresso.onView(withText("Confirm")).perform(click());
        //Ensure new name is testing
        Espresso.onView(ViewMatchers.withId(R.id.nameTextView)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.nameTextView)).check(matches(withText("Testing")));
    }
}
