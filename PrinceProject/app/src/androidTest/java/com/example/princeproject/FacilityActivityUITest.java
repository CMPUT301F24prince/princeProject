package com.example.princeproject;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
//From chatgpt, openai, "write a java UI test case of US 02.01.03 As an organizer, I want to create and manage my facility profile.", 2024-12-02
public class FacilityActivityUITest {

    @Rule
    public ActivityScenarioRule<FacilityActivity> activityRule =
            new ActivityScenarioRule<>(FacilityActivity.class);

    @Test
    public void testCancelEditFacilityProfile() {
        // Verify initial text values
        onView(withId(R.id.facility_name_text)).check(matches(withText("Facility Name")));
        onView(withId(R.id.facility_description_text)).check(matches(withText("Description")));
        onView(withId(R.id.facility_location_text)).check(matches(withText("Location")));

        // Click the edit button
        onView(withId(R.id.edit_facility_button)).perform(click());

        // Modify the facility details in the dialog
        onView(withId(R.id.facility_name)).perform(replaceText("Temporary Name"));
        onView(withId(R.id.facility_location)).perform(replaceText("Temporary Location"));
        onView(withId(R.id.facility_description)).perform(replaceText("Temporary Description"));

        // Close the keyboard to ensure visibility
        closeSoftKeyboard();

        // Click "Cancel" in the dialog
        onView(withText("Cancel")).perform(click());

        // Verify that the original details are still displayed (no changes applied)
        onView(withId(R.id.facility_name_text)).check(matches(withText("Facility Name")));
        onView(withId(R.id.facility_description_text)).check(matches(withText("Description")));
        onView(withId(R.id.facility_location_text)).check(matches(withText("Location")));
    }
}