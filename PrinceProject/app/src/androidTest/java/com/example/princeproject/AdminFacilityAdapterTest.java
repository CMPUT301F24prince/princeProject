package com.example.princeproject;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.widget.Toast;

import com.example.princeproject.AdminPage.AdminFacilityAdapter;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class AdminFacilityAdapterTest {

    private AdminFacilityAdapter adminFacilityAdapter;
    private FirebaseFirestore mockFirestore;
    private Context mockContext;
    private List<Facility> facilityList;
    private Facility mockFacility;

    @Mock
    private DocumentReference mockDocumentReference;

    @Mock
    private DocumentSnapshot mockDocumentSnapshot;

    @Before
    public void setUp() {
        mockFirestore = Mockito.mock(FirebaseFirestore.class);
        mockContext = Mockito.mock(Context.class);
        facilityList = new ArrayList<>();

        mockFacility = new Facility("1", "Test Facility", "Test Description", "Test Location", "mockBase64ImageEncode");
        facilityList.add(mockFacility);

        adminFacilityAdapter = new AdminFacilityAdapter(mockContext, facilityList, mockFirestore);

        Mockito.when(mockFirestore.collection("facilities")).thenReturn(Mockito.mock(CollectionReference.class));
        Mockito.when(mockFirestore.collection("facilities").document("1")).thenReturn(mockDocumentReference);
    }

    @Test
    public void testRemoveImage() {
        TaskCompletionSource<DocumentSnapshot> taskCompletionSource = new TaskCompletionSource<>();

        Mockito.when(mockDocumentReference.get()).thenReturn(taskCompletionSource.getTask());
        Mockito.when(mockDocumentSnapshot.getString("image")).thenReturn("mockImageData");

        Mockito.doNothing().when(mockDocumentReference).update("image", null);
        taskCompletionSource.setResult(mockDocumentSnapshot);
        adminFacilityAdapter.removeImage(mockFacility.getOrganizer_id(), 0);


        assertNull(mockFacility.getBase64ImageEncode());

    }



    @Test
    public void testDeleteFacility() {
        Mockito.doNothing().when(mockDocumentReference).delete();

        adminFacilityAdapter.deleteFacility(mockFacility.getOrganizer_id(), 0);
        Mockito.verify(mockDocumentReference, Mockito.times(1)).delete();

        assertTrue(facilityList.isEmpty());

    }
}
