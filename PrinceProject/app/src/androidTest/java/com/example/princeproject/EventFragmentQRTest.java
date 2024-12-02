package com.example.princeproject;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.princeproject.EventsPage.EventsFragment;
import com.example.princeproject.MainActivity;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class EventsFragmentQRTest {

    @Test
    public void testHandleQrScanResultWithToastVerification() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        scenario.onActivity(activity -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();

            // Access the fragment using the appropriate tag (adjust "f0" for the tab position)
            EventsFragment eventsFragment = (EventsFragment) fragmentManager.findFragmentByTag("f0");

            if (eventsFragment != null && eventsFragment.isVisible()) {
                // Simulate a QR code result
                Intent resultData = new Intent();
                resultData.putExtra("SCAN_RESULT", "Test QR Data");

                // Use a Handler to run the method on the main thread
                new Handler(activity.getMainLooper()).post(() -> {
                    try {
                        // Use reflection to access the private onActivityResult method
                        Method onActivityResultMethod = EventsFragment.class.getDeclaredMethod(
                                "onActivityResult", int.class, int.class, Intent.class);
                        onActivityResultMethod.setAccessible(true);

                        // Call the method with simulated data
                        onActivityResultMethod.invoke(eventsFragment, 1001, Activity.RESULT_OK, resultData);

                        // Verify the Toast message
                        onView(withText("QR Code scanned: Test QR Data"))
                                .inRoot(new ToastMatcher())
                                .check(matches(withText("QR Code scanned: Test QR Data")));

                    } catch (Exception e) {
                        e.printStackTrace();
                        fail("Reflection failed to access onActivityResult: " + e.getMessage());
                    }
                });
            } else {
                fail("EventsFragment not found or not visible.");
            }
        });
    }

}