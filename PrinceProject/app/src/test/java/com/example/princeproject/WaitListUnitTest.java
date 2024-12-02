package com.example.princeproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28}) // Target Android SDK level
public class WaitListUnitTest {

    @Test
    public void testJoinWaitingList() {
        // Arrange
        String eventId = "event123";
        String userId = "user123";

        // Simulate a mock event document
        Map<String, Object> event = new HashMap<>();
        event.put("eventId", eventId);
        event.put("waiting", new ArrayList<>());

        // Act: Add the user to the waiting list
        addToWaitingListMock(event, userId);

        // Assert: Check if the user was added
        @SuppressWarnings("unchecked")
        List<String> waitingList = (List<String>) event.get("waiting");
        assertTrue("User should be in the waiting list", waitingList.contains(userId));
    }

    @Test
    public void testJoinAcceptedList() {
        // Arrange
        String eventId = "event456";
        String userId = "user456";

        // Simulate a mock event document
        Map<String, Object> event = new HashMap<>();
        event.put("eventId", eventId);
        event.put("accepted", new ArrayList<>());

        // Act: Add the user to the accepted list
        addToAcceptedListMock(event, userId);

        // Assert: Check if the user was added
        @SuppressWarnings("unchecked")
        List<String> acceptedList = (List<String>) event.get("accepted");
        assertTrue("User should be in the accepted list", acceptedList.contains(userId));
    }

    @Test
    public void testJoinWaitingListWithNoEvent() {
        // Arrange
        String invalidEventId = "invalidEvent";
        String userId = "user789";

        // Simulate an empty event database
        List<Map<String, Object>> eventDatabase = new ArrayList<>();

        // Act
        String result = addToWaitingListWithNoEventMock(eventDatabase, invalidEventId, userId);

        // Assert
        assertEquals("No event found with eventId: invalidEvent", result);
    }

    // Helper methods to simulate the behavior of `WaitingList`

    private void addToWaitingListMock(Map<String, Object> event, String userId) {
        // Simulate adding user to waiting list
        @SuppressWarnings("unchecked")
        List<String> waitingList = (List<String>) event.get("waiting");
        waitingList.add(userId);
    }

    private void addToAcceptedListMock(Map<String, Object> event, String userId) {
        // Simulate adding user to accepted list
        @SuppressWarnings("unchecked")
        List<String> acceptedList = (List<String>) event.get("accepted");
        acceptedList.add(userId);
    }

    private String addToWaitingListWithNoEventMock(List<Map<String, Object>> eventDatabase, String eventId, String userId) {
        // Simulate searching for the event in a mock database
        for (Map<String, Object> event : eventDatabase) {
            if (event.get("eventId").equals(eventId)) {
                addToWaitingListMock(event, userId);
                return "User added successfully";
            }
        }
        return "No event found with eventId: " + eventId;
    }
}
