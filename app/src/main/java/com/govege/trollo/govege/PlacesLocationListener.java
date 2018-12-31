package com.govege.trollo.govege;

import android.content.Context;
import android.location.*;
import android.location.Location;
import android.os.Bundle;

/**
 * Created by Trollo on 2018-12-15.
 */

public class PlacesLocationListener implements LocationListener {
    private Location location;

    public PlacesLocationListener(PlacesLocationService service){
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public Location getLocation(){
        return location;
    }
}
