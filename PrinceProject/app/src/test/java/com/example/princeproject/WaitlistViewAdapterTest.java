package com.example.princeproject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class WaitlistViewAdapterTest {

    private FirebaseFirestore mockDb;
    private Context context;
    private WaitlistViewAdapter adapter;
    private List<String> events;
    private List<String> eventIds;
    private String userId = "testUserId";

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        mockDb = mock(FirebaseFirestore.class);
        events = new ArrayList<>(Arrays.asList("Event 1", "Event 2"));
        eventIds = new ArrayList<>(Arrays.asList("event1", "event2"));

        adapter = new WaitlistViewAdapter(context, events, eventIds, userId);
        adapter.db = mockDb;
    }

    @Test
    public void testRemoveUserFromWaitlist() {
        View itemView = adapter.getView(0, null, null);
        Button removeButton = itemView.findViewById(R.id.removeButton);
        removeButton.performClick();

        verify(mockDb.collection("events").document("event1"))
                .update("waiting", FieldValue.arrayRemove(userId));

        assertEquals(1, adapter.getCount());
        assertEquals("Event 2", adapter.getItem(0));
    }
}

