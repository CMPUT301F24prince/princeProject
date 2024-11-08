package com.example.princeproject;

import com.example.princeproject.EventsPage.Event;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventTests {
    private Event mockEvent() {
        User organizer = new User("John Doe","something@something.ca","8408408400","User", "someId");
        Date date1 = new Date();
        Date date2 = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            date1 = dateFormat.parse("2003-09-20");
            date2 = dateFormat.parse("2024-11-07");
        }
        catch (ParseException e) {
        }

        Event event = new Event("42", "Event Name", "Event Desccription", date1, date2,"my house",20, organizer, true);
        return event;
    }

    //Testing use of function that returns by reference
    @Test
    public void testUserSetter() {
        Event event = mockEvent();
        User organizer = new User("John Doe","something@something.ca","8408408400","User", "someId");

        Event event2 = mockEvent();
        User organizer2 = new User("John Day","something@something.ca","8408408400","User", "someId");
        event2.setOrganizer(organizer2);

        assertNotEquals(event.getOrganizer().getName(), event2.getOrganizer().getName());
        assertEquals(event.getOrganizer().getEmail(), event2.getOrganizer().getEmail());
    }



}
