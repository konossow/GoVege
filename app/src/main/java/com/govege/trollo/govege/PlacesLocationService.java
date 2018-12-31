package com.govege.trollo.govege;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

/**
 * Created by Trollo on 2018-12-15.
 */

public class PlacesLocationService {
    private LocationManager locationManager;
    private PlacesLocationListener placesLocationListener;
    private Location currentLocation;

    public PlacesLocationService(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.placesLocationListener = new PlacesLocationListener(this);
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, placesLocationListener);
        this.currentLocation = new Location(0,0);
    }

    public Location getLocation(){
        android.location.Location location = placesLocationListener.getLocation();
        currentLocation.setLatitude(location.getLatitude());
        currentLocation.setLongitude(location.getLongitude());
        return currentLocation;
    }
}
