package com.example.princeproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.content.Context;
import android.provider.Settings;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.princeproject.MainActivity;
import com.example.princeproject.NotificationsPage.NotificationPreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
//checking the notification preference US 01.04.03
@RunWith(AndroidJUnit4.class)
public class NotificationPreferenceManagerTest {

    private FirebaseFirestore db;

    private static final int R_ID_NOTIFICATION_TOGGLE = R.id.notification_toggle;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        // Removed animation disabling code
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    // Rest of the code remains the same
    @Test
    public void testNotificationPreferenceChange() throws InterruptedException {
        String userId = "testUser123";
        boolean newPreference = false;

        // Prepare initial user data in Firestore
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("Allow Notification", true);  // Initially set to true

        db.collection("users")
                .document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    // Now toggle the notification preference UI
                    onView(withId(R_ID_NOTIFICATION_TOGGLE)).perform(click());

                    // Update the notification preference in Firestore
                    NotificationPreferenceManager notificationPreferenceManager = new NotificationPreferenceManager();
                    notificationPreferenceManager.setNotificationPreference(userId, newPreference);

                    // Wait for Firestore update to complete (this is asynchronous)
                    try {
                        Thread.sleep(2000); // Allow time for Firestore to update
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Verify Firestore update
                    db.collection("users")
                            .document(userId)
                            .get()
                            .addOnSuccessListener(documentSnapshot -> {
                                boolean updatedPreference = documentSnapshot.getBoolean("Allow Notification");

                                // Verify that the preference is updated correctly
                                assert updatedPreference == newPreference;
                            });

                    // Verify that the UI toggle reflects the change
                    onView(withId(R_ID_NOTIFICATION_TOGGLE)).check(matches(isNotChecked()));
                });
    }
}