package com.example.princeproject;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.princeproject.AdminPage.AdminActivity;
import com.example.princeproject.AdminPage.AdminEventFragment;
import com.example.princeproject.AdminPage.AdminFacilityFragment;
import com.example.princeproject.AdminPage.AdminProfileFragment;
import com.example.princeproject.EventsPage.EventsFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AdminPageTest {


    @Before
    public void setUp() {
        Intents.init();
    }

    @Rule
    public ActivityScenarioRule<AdminActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AdminActivity.class);


    /**
     * UI tests for User Stories: US 03.01.01, US 03.03.02, US 03.03.01, US 03.06.01
     * Tests if the relevant buttons appear on the view, and that the event images are able to be browsed
     */
    @Test
    public void testRemovalButtonsAndImageEvent() {
        FragmentScenario<AdminEventFragment> scenario = FragmentScenario.launchInContainer(AdminEventFragment.class);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the Remove QR button exists inside the event list view at the first position
        onData(anything())
                .inAdapterView(withId(R.id.event_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.remove_qr_text))
                .check(ViewAssertions.matches(isDisplayed()));

        // Check if the "Remove Picture" button exists inside the event list view at the first position
        onData(anything())
                .inAdapterView(withId(R.id.event_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.remove_picture_text))
                .check(ViewAssertions.matches(isDisplayed()));

        // Check if the "Delete Event" button exists inside the event list view at the first position
        onData(anything())
                .inAdapterView(withId(R.id.event_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.delete_button_event))
                .check(ViewAssertions.matches(isDisplayed()));

        // Check if the image is loaded into the ImageView
        onData(anything())
                .inAdapterView(withId(R.id.event_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.event_image))
                .check(matches(isDisplayed()));

    }

    /**
     * UI tests for User Stories: US 03.07.01, US 03.03.01, US 03.06.01
     * Tests if the relevant buttons appear on the view, and that the facility images are able to be browsed
     */
    @Test
    public void testRemovalButtonsAndImageFacility() {
        // Launch the AdminFacilityFragment
        FragmentScenario<AdminFacilityFragment> scenario = FragmentScenario.launchInContainer(AdminFacilityFragment.class);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the "Delete Facility" button exists inside the facility list view at the first position
        onData(anything())
                .inAdapterView(withId(R.id.facility_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.delete_button))
                .check(ViewAssertions.matches(isDisplayed()));

        // Check if the "Remove Picture" button exists inside the facility list view at the first position
        onData(anything())
                .inAdapterView(withId(R.id.facility_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.remove_picture_text))
                .check(ViewAssertions.matches(isDisplayed()));

        // Check if the image is loaded into the ImageView
        onData(anything())
                .inAdapterView(withId(R.id.facility_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.user_image))
                .check(matches(isDisplayed()));
    }


    /**
     * UI tests for User Stories: US 03.02.01, US 03.03.01, US 03.06.01
     * Tests if the relevant buttons appear on the view, and that the profile images are able to be browsed
     */
    @Test
    public void testRemovalButtonsAndImageProfile() {
        FragmentScenario<AdminProfileFragment> scenario = FragmentScenario.launchInContainer(AdminProfileFragment.class);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the delete button is present in the first item of the ListView
        onData(anything())
                .inAdapterView(withId(R.id.user_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.delete_button))
                .check(ViewAssertions.matches(isDisplayed()));

        // Check if the remove image button is present in the first item of the ListView
        onData(anything())
                .inAdapterView(withId(R.id.user_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.remove_picture_text))
                .check(ViewAssertions.matches(isDisplayed()));

        // Check if the image is loaded into the ImageView
        onData(anything())
                .inAdapterView(withId(R.id.user_list_view))
                .atPosition(0)
                .onChildView(withId(R.id.user_image))
                .check(matches(isDisplayed()));
    }


    /**
     * UI test case for User Story: US 03.05.01
     * Tests to see if the list of profiles shows up to be able to browse.
     */
    @Test
    public void testListViewPopulatedInAdminProfileFragment() {
        ActivityScenario.launch(AdminActivity.class);
        FragmentScenario.launchInContainer(AdminProfileFragment.class);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.user_list_view)).check(matches(isDisplayed()));

    }

    /**
     * UI test case for User Story: US 03.04.01
     * Tests to see if the list of events shows up to be able to browse.
     */
    @Test
    public void testListViewPopulatedInAdminEventFragment() {
        ActivityScenario.launch(AdminActivity.class);
        FragmentScenario.launchInContainer(AdminEventFragment.class);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.event_list_view)).check(matches(isDisplayed()));

    }

    /**
     * UI test case to test if the list of facilities show up
     */
    @Test
    public void testListViewPopulatedInAdminFacilityFragment() {
        ActivityScenario.launch(AdminActivity.class);
        FragmentScenario.launchInContainer(AdminFacilityFragment.class);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.facility_list_view)).check(matches(isDisplayed()));

    }

    /**
     * UI test case to test if the admin page button correctly launches the admin activity
     */
    @Test
    public void testAdminPageButtonLaunchesAdminActivity() {
        FragmentScenario.launchInContainer(EventsFragment.class);

        onView(withId(R.id.admin_button)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(AdminActivity.class.getName()));

    }

    @After
    public void tearDown() {
        Intents.release(); 
    }
}
