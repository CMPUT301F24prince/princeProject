package com.example.princeproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Class to represent an facility object
 * */
public class Facility {

    private String organizer_id;
    private String location;
    private String name;
    private String description;
    private String base64ImageEncode;

    /**
     * Constructor for a facility without a profile image
     * @param organizer_id
     *      The organizer of the facility
     * @param name
     *      The name of the facility
     * @param location
     *      The location of the facility
     * @param description
     *      The description of the facility
     * */
    public Facility(String organizer_id, String location, String name, String description) {
        this.organizer_id = organizer_id;
        this.location = location;
        this.name = name;
        this.description = description;
    }

    /**
     * Constructor for a facility with a provided image
     * @param organizer_id
     *      The organizer of the facility
     * @param name
     *      The name of the facility
     * @param location
     *      The location of the facility
     * @param description
     *      The description of the facility
     * @param encode
     *      The encoded image of the facility
     * */
    public Facility(String organizer_id, String location, String name, String description, String encode) {
        this.organizer_id = organizer_id;
        this.location = location;
        this.name = name;
        this.description = description;
        this.base64ImageEncode = encode;
    }

    /**
     * Getter for the facility location
     * @return
     *      The location of the facility
     * */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for the facility location
     * @param location
     *      The location of the facility
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for the facility organizer
     * @return
     *      The organizer id of the facility
     * */
    public String getOrganizer_id() {
        return organizer_id;
    }

    /**
     * Setter for the facility location
     * @param organizer_id
     *      The organizer id of the facility
     * */
    public void setOrganizer_id(String organizer_id) {
        this.organizer_id = organizer_id;
    }

    /**
     * Getter for the facility name
     * @return
     *      The name of the facility
     * */
    public String getName() {
        return name;
    }

    /**
     * Setter for the facility name
     * @param name
     *      The name of the facility
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the facility description
     * @return
     *      The description of the facility
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the facility description
     * @param description
     *      The description of the facility
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the facility encoded image
     * @return
     *      The encoded image of the facility
     * */
    public String getBase64ImageEncode() {
        return this.base64ImageEncode;
    }

    /**
     * Setter for the facility image
     * @param base64ImageEncode
     *      The encoded image of the facility
     * */
    public void setBase64ImageEncode(String base64ImageEncode) {this.base64ImageEncode = base64ImageEncode;}

    /**
     * Decodes the encoded image of the facility
     * @param context
     *      The current context
     * @param facilityImageEncode
     *      The encoded image string
     * */
    public static android.net.Uri decodeBase64String(Context context, String facilityImageEncode) {
        Calendar calendar = Calendar.getInstance();
        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(calendar.get(Calendar.MINUTE));
        String second = Integer.toString(calendar.get(Calendar.SECOND));
        String milisecond = Integer.toString(calendar.get(Calendar.MILLISECOND));

        if (!(facilityImageEncode == null)) {
            byte[] decodedBytes = Base64.decode(facilityImageEncode, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            File outputFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_"+hour+minute+second+milisecond+".png");
            try {
                FileOutputStream fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Uri.fromFile(outputFile);
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_android_black_24dp);

            File outputFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_"+hour+minute+second+milisecond+".png");
            try {
                FileOutputStream fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Uri.fromFile(outputFile);
        }
    }
}
