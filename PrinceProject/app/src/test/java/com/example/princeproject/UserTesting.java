package com.example.princeproject;

import android.graphics.Bitmap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
//From chatgpt, openai, "write a java Unit test case for the user class", 2024-12-02
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28}) // Set the target SDK level
public class UserTesting {

    @Test
    public void testUserConstructorAndGetters() {
        // Arrange
        String name = "Alice";
        String email = "alice@example.com";
        String phone = "1234567890";
        String account = "user";
        String deviceId = "device123";

        // Act
        User user = new User(name, email, phone, account, deviceId);

        // Assert
        assertEquals("Name should match", name, user.getName());
        assertEquals("Email should match", email, user.getEmail());
        assertEquals("Phone should match", phone, user.getPhone());
        assertEquals("Account type should match", account, user.getAccount());
        assertEquals("Device ID should match", deviceId, user.getDeviceId());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        User user = new User("Alice", "alice@example.com", null, "user", "device123");

        // Act
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setPhone("9876543210");
        user.setAccount("admin");
        user.setDeviceId("device456");

        // Assert
        assertEquals("Name should match", "Bob", user.getName());
        assertEquals("Email should match", "bob@example.com", user.getEmail());
        assertEquals("Phone should match", "9876543210", user.getPhone());
        assertEquals("Account type should match", "admin", user.getAccount());
        assertEquals("Device ID should match", "device456", user.getDeviceId());
    }

    @Test
    public void testGenerateProfileImage() {
        // Arrange
        User user = new User("Alice", "alice@example.com", null, "user", "device123");

        // Act
        user.GenerateProfileImage();
        String profilePictureEncode = user.getProfilePictureEncode();

        // Assert
        assertNotNull("Profile picture encode should not be null", profilePictureEncode);
        assertFalse("Profile picture encode should not be empty", profilePictureEncode.isEmpty());
    }

    @Test
    public void testProfilePictureEncodeGetterSetter() {
        // Arrange
        User user = new User("Alice", "alice@example.com", null, "user", "device123");
        String encodedString = "exampleEncodedString";

        // Act
        user.setProfilePictureEncode(encodedString);

        // Assert
        assertEquals("Profile picture encode should match", encodedString, user.getProfilePictureEncode());
    }

    @Test
    public void testDecodeBase64StringWithNullProfilePicture() {
        // Arrange
        User user = new User("Alice", "alice@example.com", null, "user", "device123");

        // Act
        android.net.Uri result = user.decodeBase64String(null); // Context is mocked by Robolectric

        // Assert
        assertNull("Decode should return null for null profile picture", result);
    }
}
