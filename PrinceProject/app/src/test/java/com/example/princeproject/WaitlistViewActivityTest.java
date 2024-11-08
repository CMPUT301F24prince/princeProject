package com.example.princeproject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.Task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class WaitlistViewActivityTest {

    private FirebaseFirestore mockDb;

    @Before
    public void setUp() {
        mockDb = mock(FirebaseFirestore.class);
    }

    @Test
    public void testLoadWaitlistedEvents() {
        ActivityScenario<WaitlistViewActivity> scenario = ActivityScenario.launch(WaitlistViewActivity.class);
        scenario.onActivity(activity -> {
            ListView waitlistEventsListView = activity.findViewById(R.id.waitlistEventsListView);

            Task<QuerySnapshot> mockTask = mock(Task.class);  // Mock the Task
            QuerySnapshot mockQuerySnapshot = mock(QuerySnapshot.class);  // Mock the QuerySnapshot

            when(mockDb.collection("events").whereArrayContains("waiting", activity.userId).get())
                    .thenReturn(mockTask);

            when(mockTask.isSuccessful()).thenReturn(true);
            when(mockTask.getResult()).thenReturn(mockQuerySnapshot);

            DocumentSnapshot mockDocument1 = mock(DocumentSnapshot.class);
            when(mockDocument1.getString("name")).thenReturn("Event 1");
            when(mockDocument1.getString("eventId")).thenReturn("event1");

            DocumentSnapshot mockDocument2 = mock(DocumentSnapshot.class);
            when(mockDocument2.getString("name")).thenReturn("Event 2");
            when(mockDocument2.getString("eventId")).thenReturn("event2");

            List<DocumentSnapshot> mockDocuments = Arrays.asList(mockDocument1, mockDocument2);
            when(mockQuerySnapshot.getDocuments()).thenReturn(mockDocuments);


            activity.loadWaitlistedEvents();


            assertEquals(2, waitlistEventsListView.getAdapter().getCount());
            assertEquals("Event 1", ((WaitlistViewAdapter) waitlistEventsListView.getAdapter()).getItem(0));
            assertEquals("Event 2", ((WaitlistViewAdapter) waitlistEventsListView.getAdapter()).getItem(1));
        });
    }
}


