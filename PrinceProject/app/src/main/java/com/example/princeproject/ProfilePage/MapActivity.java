package com.example.princeproject.ProfilePage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.princeproject.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class that handles the map activity, when an Organizer tries to open the map for
 * their events
 * */
public class MapActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ImageView mapImageView;
    private String eventId;

    /**
     * Method to handle the creation of the map activity instance
     * @param savedInstanceState
     *      The current state
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);

        //Load in the image view for map
        mapImageView = findViewById(R.id.mapImageView);
        db = FirebaseFirestore.getInstance();

        //Get the eventId passed from ProfileFragment
        eventId = getIntent().getStringExtra("eventId");

        //Fetch locations with the eventId
        if (eventId != null) {
            fetchLocationsAndDisplay(eventId);
        }
    }

    /**
     * Method to get all latitude and longitude points associated with an event
     * id
     * @param eventId
     *      The event to load in the location for
     * */
    private void fetchLocationsAndDisplay(String eventId){
        //Query database for all locations for the specific event
        db.collection("locations")
                .whereEqualTo("eventId",eventId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //Lists for latitudes and longitudes
                    List<Double> latitudes = new ArrayList<>();
                    List<Double> longitudes = new ArrayList<>();

                    //Get all locations for the event
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        //Get the latitude and longitude from the document
                        double latitude = document.getDouble("latitude");
                        double longitude = document.getDouble("longitude");

                        //Add the values to their respective lists
                        latitudes.add(latitude);
                        longitudes.add(longitude);
                    }

                    //Draw locations on the map
                    drawLocationsOnMap(latitudes, longitudes);
                });

    }

    /**
     * Method to draw the latitudes and longitudes on the map of Edmonton
     * @param latitudes
     *      The list of latitudes
     * @param longitudes
     *      The list of longitudes
     * */
    private void drawLocationsOnMap(List<Double> latitudes, List<Double> longitudes){
        //Load the map image
        Bitmap mapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.edmonton_map);
        Bitmap mutableBitmap = mapBitmap.copy(Bitmap.Config.ARGB_8888, true);

        //Create a canvas to draw on the image
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        //Specify the map bounds (based on the picture of the map)
        double mapLeftLongitude = -113.719411;
        double mapRightLongitude = -113.247052;
        double mapTopLatitude = 53.664884;
        double mapBottomLatitude = 53.398754;

        //Get the width and height of the image
        int mapWidth = mutableBitmap.getWidth();
        int mapHeight = mutableBitmap.getHeight();

        //Draw each location on the map
        for (int i = 0; i < latitudes.size(); i++) {
            double latitude = latitudes.get(i);
            double longitude = longitudes.get(i);
            float x = (float) ((longitude - mapLeftLongitude) / (mapRightLongitude - mapLeftLongitude) * mapWidth);
            float y = (float) ((mapTopLatitude - latitude) / (mapTopLatitude - mapBottomLatitude) * mapHeight);
            canvas.drawCircle(x, y, 25, paint);
        }

        //Display the updated map
        mapImageView.setImageBitmap(mutableBitmap);
    }
}
