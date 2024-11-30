package com.example.princeproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Calendar;

/**
 * This is a class that defines a user account for the app
 * */
public class User implements Serializable {

    //Attributes for a user
    private String name;
    private String email;
    private String phone;
    private String account; //Account can be user, organizer, or admin
    private String deviceId;
    private String profilePictureEncode;

    /**
     * This is a constructor for creating a user object
     * @param name
     *      Name of the user
     * @param email
     *      Email of the user
     * @param phone
     *      Phone number of the user (Optional, can be null)
     * @param account
     *      Type of the account of the user (user, organizer, admin)
     * */
    public User(String name, String email, String phone, String account, String deviceId){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.account = account;
        this.deviceId = deviceId;


    }

    /**
     * Gets the name of the user
     * @return
     *      Name of the user
     * */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user
     * @param name
     *      Name of the user
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user
     * @return
     *      Email of the user
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user
     * @param email
     *      Email of the user
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user
     * @return
     *      Phone number of the user
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user
     * @param phone
     *      Phone number of the user
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the account type of the user
     * @return
     *      Account type of the user
     * */
    public String getAccount() {
        return account;
    }

    /**
     * Sets the account type of the user
     * @param account
     *      Account type of the user
     * */
    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setProfilePictureEncode(String encode) {this.profilePictureEncode = encode;}

    public String getProfilePictureEncode() {return this.profilePictureEncode; }

    public void GenerateProfileImage() {
        // Create profile image
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0xFFFF0000);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        this.profilePictureEncode = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public android.net.Uri decodeBase64String(Context context) {
        Calendar calendar = Calendar.getInstance();
        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(calendar.get(Calendar.MINUTE));
        String second = Integer.toString(calendar.get(Calendar.SECOND));
        String milisecond = Integer.toString(calendar.get(Calendar.MILLISECOND));

        if (!(this.profilePictureEncode == null)) {
            byte[] decodedBytes = Base64.decode(this.profilePictureEncode, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            File outputFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_"+hour+minute+second+milisecond+".png");
            try {
                FileOutputStream fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //if (outputFile.exists()) {
            //    outputFile.delete();
            //}
            return Uri.fromFile(outputFile);
        }
        else {
            return null;
        }
    }
}
