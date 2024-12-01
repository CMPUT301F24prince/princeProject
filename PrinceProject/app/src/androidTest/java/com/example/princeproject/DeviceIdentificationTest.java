package com.example.princeproject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DeviceIdentificationTest {

    @Mock
    private FirebaseFirestore mockFirestore;

    @Mock
    private DocumentSnapshot mockDocumentSnapshot;

    private DatabaseInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Inject the mocked Firestore instance into the interactor
        interactor = new DatabaseInteractor(mockFirestore);
    }

    @Test
    public void testDeviceIdentification() {
        String mockDeviceId = "testDevice123";
        String userId = "testUser123";

        // Mock Firestore behavior
        when(mockFirestore.collection("users").document(userId).get())
                .thenReturn(mock(Task.class)); // Mock Firestore `get()` call

        when(mockDocumentSnapshot.getString("deviceId"))
                .thenReturn(mockDeviceId); // Mock document content

        // Simulate storing device ID
        interactor.storeDeviceIdForUser(userId, mockDeviceId);

        // Verify Firestore interaction
        verify(mockFirestore.collection("users").document(userId)).set(anyMap());
    }
}