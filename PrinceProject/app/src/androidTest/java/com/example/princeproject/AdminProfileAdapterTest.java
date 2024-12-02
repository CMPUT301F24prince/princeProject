package com.example.princeproject;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.widget.Toast;

import com.example.princeproject.AdminPage.AdminProfileAdapter;
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

public class AdminProfileAdapterTest {

    private AdminProfileAdapter adminProfileAdapter;
    private FirebaseFirestore mockFirestore;
    private Context mockContext;
    private List<User> userList;
    private User mockUser;

    @Mock
    private DocumentReference mockDocumentReference;

    @Mock
    private DocumentSnapshot mockDocumentSnapshot;

    @Before
    public void setUp() {
        mockFirestore = Mockito.mock(FirebaseFirestore.class);
        mockContext = Mockito.mock(Context.class);
        userList = new ArrayList<>();

        mockUser = new User("1", "Test User", "test@example.com", "user", "mockDeviceId");
        userList.add(mockUser);

        adminProfileAdapter = new AdminProfileAdapter(mockContext, userList, mockFirestore);

        Mockito.when(mockFirestore.collection("users")).thenReturn(Mockito.mock(CollectionReference.class));
        Mockito.when(mockFirestore.collection("users").document("1")).thenReturn(mockDocumentReference);
    }

    @Test
    public void testRemovePicture() {
        TaskCompletionSource<DocumentSnapshot> taskCompletionSource = new TaskCompletionSource<>();

        Mockito.when(mockDocumentReference.get()).thenReturn(taskCompletionSource.getTask());
        Mockito.when(mockDocumentSnapshot.getString("profilePicture")).thenReturn("mockImageData");
        Mockito.when(mockDocumentSnapshot.getBoolean("default")).thenReturn(false);

        Mockito.doNothing().when(mockDocumentReference).update(Mockito.anyMap());
        taskCompletionSource.setResult(mockDocumentSnapshot);

        adminProfileAdapter.removePicture(mockUser.getDeviceId(), 0);

    }

    @Test
    public void testDeleteUser() {
        Mockito.doNothing().when(mockDocumentReference).delete();
        adminProfileAdapter.deleteUser(mockUser.getDeviceId(), 0);

        assertTrue(userList.isEmpty());

    }
}
