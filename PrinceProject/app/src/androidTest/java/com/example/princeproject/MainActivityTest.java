package com.example.princeproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.princeproject.ProfilePage.ProfileFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenerio = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testEditProfile() throws InterruptedException {
        //Check if the main page is on screen
        onView(withId(R.id.main_navigation_bar)).check(matches(isDisplayed()));
        //Click the profile tab
        onView(allOf(isDescendantOfA(withId(R.id.main_navigation_bar)),withText("My Profile"))).perform(click());
        //Wait for the profile to show up
        onView(withId(R.id.editProfileButton)).check(matches(isDisplayed()));
        //Click on edit profile
        onView(withText("Edit Profile")).perform(click());
        //Wait for the fragment to open up
        onView(withText("Edit Profile Details")).check(matches(isDisplayed()));
        onView(withId(R.id.editEditTextName)).check(matches(isDisplayed()));
        //Change the name to testing name
        onView(withId(R.id.editTextName)).perform(ViewActions.typeText("Testing Name"));
        //Click on the confirm button
        onView(withText("Confirm")).perform(click());
        //Check that the name changed in profile
        onView(withText("Testing Name")).check(matches(isDisplayed()));
    }
}
