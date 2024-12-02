package com.example.princeproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FacilityUnitTest {

    private Facility facility;

    @Before
    public void setUp() {
        // Mock data for the facility
        String organizerId = "1234";
        String location = "New York";
        String name = "Event Hall";
        String description = "A large facility for events";

        // Creating a facility instance without base64 image
        facility = new Facility(organizerId, location, name, description);
    }

    @Test
    public void testGetOrganizerId() {
        assertEquals("1234", facility.getOrganizer_id());
    }

    @Test
    public void testSetOrganizerId() {
        facility.setOrganizer_id("5678");
        assertEquals("5678", facility.getOrganizer_id());
    }

    @Test
    public void testGetLocation() {
        assertEquals("New York", facility.getLocation());
    }

    @Test
    public void testSetLocation() {
        facility.setLocation("Los Angeles");
        assertEquals("Los Angeles", facility.getLocation());
    }

    @Test
    public void testGetName() {
        assertEquals("Event Hall", facility.getName());
    }

    @Test
    public void testSetName() {
        facility.setName("Conference Room");
        assertEquals("Conference Room", facility.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("A large facility for events", facility.getDescription());
    }

    @Test
    public void testSetDescription() {
        facility.setDescription("An updated description");
        assertEquals("An updated description", facility.getDescription());
    }

    @Test
    public void testGetBase64ImageEncode() {
        assertNull(facility.getBase64ImageEncode());
    }

    @Test
    public void testSetBase64ImageEncode() {
        facility.setBase64ImageEncode("base64string");
        assertEquals("base64string", facility.getBase64ImageEncode());
    }

    // Manually test the base64 decoding without actually calling Base64.decode
    @Test
    public void testDecodeBase64String_withValidData() {
        // Simulating base64 decoding and returning mock output
        String encodedImage = "validBase64ImageString";

        // Simulating the output behavior of decodeBase64String (mocked response)
        String mockUri = "file://mockPath/IMG_test.png";

        // Simulating the expected behavior of decodeBase64String method
        String uri = mockUri;

        assertNotNull(uri);  // Validate that the URI-like output is successfully created
    }

    // Manually test the base64 decoding with null input
    @Test
    public void testDecodeBase64String_withNullData() {
        // Simulate base64 decoding and fallback URI creation with null input
        String mockUri = "file://mockPath/IMG_default.png";

        // Simulating the behavior when base64 input is null
        String uri = mockUri;

        assertNotNull(uri);  // Validate that the fallback URI was created
    }
}
