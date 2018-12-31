package com.govege.trollo.govege;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Place currentLocation;
    private Place pickedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.currentLocation = (Place) getIntent().getSerializableExtra("currentLocation");
        this.pickedPlace = (Place) getIntent().getSerializableExtra("pickedPlace");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();

        mMap.addMarker(new MarkerOptions()
                .position(currentLocation.getLocation().getLatLng())
                .title(currentLocation.getName()));
        bounds.include(currentLocation.getLocation().getLatLng());
        mMap.addMarker(new MarkerOptions()
                .position(pickedPlace.getLocation().getLatLng())
                .title(pickedPlace.getName()));
        bounds.include(pickedPlace.getLocation().getLatLng());
        try {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 250));
        }
        catch (Exception e){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pickedPlace.getLocation().getLatLng()));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(14.0f));
        }
    }
}
