package com.example.princeproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.princeproject.EventsPage.EventsFragment;
import com.example.princeproject.ProfilePage.EntrantListPage.EntrantListActivity;
import com.example.princeproject.ProfilePage.EntrantListPage.ManageEventsActivity;
import com.example.princeproject.ProfilePage.ProfileFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EntrantListUITest {

    @Rule
    public ActivityScenarioRule<EntrantListActivity> activityScenarioRule =
            new ActivityScenarioRule<>(EntrantListActivity.class);

    @Before
    public void setUp() {
        // Initialize the intents before each test
        Intents.init();
    }

    /**
     * UI test for User Story: US 02.06.01, US 02.06.04
     * Tests that the list of chosen applicants appears along with the button to cancel them
     */
    @Test
    public void testChosenTab() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.list_view))
                .check(matches(isDisplayed()));
        onView(withId(R.id.cancel_button))
                .check(matches(isDisplayed()));
    }

    /**
     * UI test for User Story: US 02.06.02
     * Tests that the list of the cancelled/declined entrants appears
     */
    @Test
    public void testDeclinedTab() {
        onView(withText("Declined")).perform(click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.list_view))
                .check(matches(isDisplayed()));
    }

    /**
     * UI test for User Story: US 02.06.03
     * Tests that the list of the accepted entrants appears
     */
    @Test
    public void testAcceptedTab() {

        onView(withText("Accepted")).perform(click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.list_view))
                .check(matches(isDisplayed()));
    }

    /**
     * UI test for User Story: US 02.02.01
     * Tests that the list of the waitlisted entrants appears
     */
    @Test
    public void testWaitingTab() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Waiting")).perform(click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.list_view))
                .check(matches(isDisplayed()));
    }

    /**
     * Checks that the manage events page appears properly
     */
    @Test
    public void testManageEventButtonLaunchesManageEventsActivity() {
        onView(withId(R.id.manage_event_button)).perform(click());

        intended(hasComponent(ManageEventsActivity.class.getName()));

    }

    @After
    public void tearDown() {
        Intents.release();
    }



}
