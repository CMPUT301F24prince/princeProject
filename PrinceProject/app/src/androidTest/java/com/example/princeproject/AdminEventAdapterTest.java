package com.example.princeproject;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import com.example.princeproject.AdminPage.AdminEventAdapter;
import com.example.princeproject.EventsPage.Event;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.TaskCompletionSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AdminEventAdapterTest {

    private AdminEventAdapter adminEventAdapter;
    private FirebaseFirestore mockFirestore;
    private Context mockContext;
    private List<Event> eventList;
    private Event mockEvent;

    @Mock
    private DocumentReference mockDocumentReference;

    @Mock
    private DocumentSnapshot mockDocumentSnapshot;

    @Before
    public void setUp() {
        mockFirestore = Mockito.mock(FirebaseFirestore.class);
        mockContext = Mockito.mock(Context.class);
        eventList = new ArrayList<>();

        mockEvent = new Event("1", "Test Event", "Test Description", "Test Location", null);

        eventList.add(mockEvent);

        adminEventAdapter = new AdminEventAdapter(mockContext, eventList, mockFirestore);

        Mockito.when(mockFirestore.collection("events")).thenReturn(Mockito.mock(CollectionReference.class));
        Mockito.when(mockFirestore.collection("events").document("1")).thenReturn(mockDocumentReference);
    }

    @Test
    public void testDeleteEvent() {
        Mockito.doNothing().when(mockDocumentReference).delete();
        adminEventAdapter.deleteEvent(mockEvent.getEventId(), 0);
        assertTrue(eventList.isEmpty());

    }

    @Test
    public void testDeleteQR() {
        TaskCompletionSource<DocumentSnapshot> taskCompletionSource = new TaskCompletionSource<>();

        Mockito.when(mockDocumentReference.get()).thenReturn(taskCompletionSource.getTask());
        Mockito.when(mockDocumentSnapshot.getString("qrHashData")).thenReturn("mockedQRHashData");

        Mockito.doNothing().when(mockDocumentReference).update("qrHashData", null);

        taskCompletionSource.setResult(mockDocumentSnapshot);

        adminEventAdapter.deleteQR(mockEvent.getEventId());


    }

    @Test
    public void testDeletePoster() {
        TaskCompletionSource<DocumentSnapshot> taskCompletionSource = new TaskCompletionSource<>();

        Mockito.when(mockDocumentReference.get()).thenReturn(taskCompletionSource.getTask());
        Mockito.when(mockDocumentSnapshot.getString("eventPosterEncode")).thenReturn("mockedPosterData");

        Mockito.doNothing().when(mockDocumentReference).update("eventPosterEncode", null);
        taskCompletionSource.setResult(mockDocumentSnapshot);
        adminEventAdapter.deletePoster(mockEvent.getEventId(), 0);

        assertNull(mockEvent.getImageEncode());

    }

}
