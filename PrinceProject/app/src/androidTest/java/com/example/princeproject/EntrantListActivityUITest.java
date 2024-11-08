package com.example.princeproject;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.princeproject.ProfilePage.EntrantListPage.EntrantListActivity;
import com.example.princeproject.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class EntrantListActivityUITest {

    @Rule
    public ActivityScenarioRule<EntrantListActivity> activityRule =
            new ActivityScenarioRule<>(EntrantListActivity.class);

    @Test
    public void testSpinnerSelection_ShouldUpdateFragment() {
        Espresso.onView(withId(R.id.event_spinner)).perform(click());
        Espresso.onData(withText("Event 1")).perform(click());
        Espresso.onView(withId(R.id.event_spinner)).check(matches(withText("Event 1")));
    }

    @Test
    public void testLotteryButton_ClicksToDrawLottery() {
        Espresso.onView(withId(R.id.lottery_button)).perform(click());
        Espresso.onView(withId(R.id.lottery_button)).check(matches(withText("Draw Replacement")));
    }

    @Test
    public void testViewPager_DisplaysTabs() {
        Espresso.onView(withText("Chosen")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withText("Declined")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withText("Accepted")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withText("Waiting")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}

