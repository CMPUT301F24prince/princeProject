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

public class Facility {

    private String organizer_id;
    private String location;
    private String name;
    private String description;
    private String base64ImageEncode;

    public Facility(String organizer_id, String location, String name, String description) {
        this.organizer_id = organizer_id;
        this.location = location;
        this.name = name;
        this.description = description;
    }

    public Facility(String organizer_id, String location, String name, String description, String encode) {
        this.organizer_id = organizer_id;
        this.location = location;
        this.name = name;
        this.description = description;
        this.base64ImageEncode = encode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganizer_id() {
        return organizer_id;
    }

    public void setOrganizer_id(String organizer_id) {
        this.organizer_id = organizer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase64ImageEncode() {
        return this.base64ImageEncode;
    }

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
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.event_filler_image);

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
