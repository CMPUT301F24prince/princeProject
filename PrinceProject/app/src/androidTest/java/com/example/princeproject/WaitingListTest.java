package com.example.princeproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class WaitingListTest {

    private FirebaseFirestore db;

    // Change this to the correct button ID from the view hierarchy
    private static final int R_ID_JOIN_BUTTON = R.id.waitlist_view;
    private static final String EVENT_ID = "event123";
    private static final String USER_ID = "user123";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        db = FirebaseFirestore.getInstance();
    }

    @Test
    public void testJoinWaitlist() throws InterruptedException {
        // Step 1: Simulate "View Waitlist" action by clicking the button
        onView(withId(R_ID_JOIN_BUTTON)).perform(click());

        // Step 2: Verify user is added to the waitlist in Firestore
        Thread.sleep(2000); // Wait for Firestore update
        db.collection("events")
                .document(EVENT_ID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    // Verify user is in the "waiting" list
                    List<String> waitingList = (List<String>) documentSnapshot.get("waiting");
                    assertTrue(waitingList.contains(USER_ID));
                });
    }
}