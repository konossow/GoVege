package com.govege.trollo.govege;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private Location currentLocation;
    private ArrayList<Place> places;
    private PlacesArrayAdapter placesArrayAdapter;
    private PlacesLocationService placesLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializePlacesLocationService();
        initializePlacesListView();
        initializeUpdateLocationHandler();
    }

    private void initializePlacesLocationService() {
        this.placesLocationService = new PlacesLocationService(this);
    }

    private void initializePlacesListView() {
        this.places = new ArrayList<>();
        this.placesArrayAdapter =  new PlacesArrayAdapter(this, R.layout.place_item, places);
        ListView placesListView = (ListView) findViewById(R.id.placesListView);
        placesListView.setAdapter(placesArrayAdapter);
        final Context thisContext = this;
        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent intent = new Intent(thisContext, MapsActivity.class);
                intent.putExtra("currentLocation", new Place("Your position", "", 0, currentLocation, false));
                intent.putExtra("pickedPlace", places.get(position));
                startActivity(intent);
            }
        });
    }

    private void initializeUpdateLocationHandler() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateLocation();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateLocation();
    }

    public void updateLocation(){
        this.currentLocation = placesLocationService.getLocation();
        ((TextView) findViewById(R.id.locationNS)).setText(String.format("%.5f", currentLocation.getLongitude()));
        ((TextView) findViewById(R.id.locationWE)).setText(String.format("%.5f", currentLocation.getLatitude()));
    }

    public void searchLocations(View v){
        updateLocation();
        if(currentLocation.getLatitude() == 0 || currentLocation.getLongitude() == 0){
            Toast.makeText(this, "Wait for your position to be evaluated!", Toast.LENGTH_SHORT).show();
            return;
        }
        int radius = 50000;
        String googlePlacesAPI = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=%d&keyword=%s&key=%s";//getConstance from settings
        String query = "vegan";
        String googlePlacesAPIKey = this.getString(R.string.google_maps_key);//"AIzaSyA5V4GvCmRTRWDrd8A9XZZAMreSjyJUZ8g";
        try {
            URL url = new URL(String.format(googlePlacesAPI, currentLocation.getLatitude(), currentLocation.getLongitude(), radius, URLEncoder.encode(query), googlePlacesAPIKey));
            String responseString = new HTTPRequester("GET").execute(url).get();
            parsePlaces(responseString);
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
        }
    }

    private void parsePlaces(String json){
        places.clear();

        try {
            final JSONObject obj = new JSONObject(json);
            final JSONArray results = obj.getJSONArray("results");
            final int n = results.length();
            for (int i = 0; i < n; ++i) {
                JSONObject place = results.getJSONObject(i);
                String name = place.getString("name");
                String address = place.getString("vicinity");
                double rating = place.getDouble("rating");
                JSONObject geometry = place.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                JSONObject openingHours = place.getJSONObject("opening_hours");
                boolean openNow = openingHours.getBoolean("open_now");
                places.add(new Place(name, address, rating, new Location(location.getDouble("lat"), location.getDouble("lng")), openNow));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(places.size() == 0){
            Toast.makeText(this, "Failed to retrieve data from server!", Toast.LENGTH_SHORT).show();
        }
        else{
            placesArrayAdapter.notifyDataSetChanged();
        }
    }

    public void openSettings(View v){

    }

    public Location getCurrentLocation(){ return currentLocation; }
}
