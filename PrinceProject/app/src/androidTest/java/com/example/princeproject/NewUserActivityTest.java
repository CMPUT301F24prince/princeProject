package com.example.princeproject;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import com.example.princeproject.NewUserActivity;
//test this US 01.02.01 As an entrant, I want to provide my personal information such as name, email and optional phone number in the app
@RunWith(AndroidJUnit4.class)
public class NewUserActivityTest {

    @Rule
    public ActivityTestRule<NewUserActivity> activityRule =
            new ActivityTestRule<>(NewUserActivity.class);

    @Test
    public void testUserInformationSubmission() {
        // Enter name
        onView(withId(R.id.editTextName)).perform(typeText("John Doe"), ViewActions.closeSoftKeyboard());

        // Enter email
        onView(withId(R.id.editTextEmail)).perform(typeText("johndoe@example.com"), ViewActions.closeSoftKeyboard());

        // Enter phone (optional)
        onView(withId(R.id.editTextPhone)).perform(typeText("1234567890"), ViewActions.closeSoftKeyboard());

        // Select account type
        onView(withId(R.id.editAccountType)).perform(click());
        onView(withText("User")).perform(click());

        // Toggle notification switch
        onView(withId(R.id.notification_switch)).perform(click());

        // Submit information
        onView(withId(R.id.submitButton)).perform(click());

        // Check if activity is finished (or perform further database validation, if needed)
        // This assumes that the activity will finish after successful submission
    }
}
