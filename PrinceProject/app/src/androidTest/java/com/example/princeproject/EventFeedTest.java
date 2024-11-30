package com.example.princeproject;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EventFeedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenerio = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void US010101() throws InterruptedException {
        onView(withId(R.id.root_pager)).perform(ViewActions.swipeRight());

        Thread.sleep(4000);

        onData(anything())
                .inAdapterView(withId(R.id.event_feed))
                .atPosition(1)
                .perform(click());

        onView(withId(R.id.joinWaitingListButton)).perform(click());
        onView(withId(R.id.waitlist_view)).perform(click());

    }
}
