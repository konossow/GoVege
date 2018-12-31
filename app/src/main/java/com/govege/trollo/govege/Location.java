package com.govege.trollo.govege;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Trollo on 2018-12-15.
 */

public class Location implements Serializable{
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Location anotherLocation){
        this.longitude = anotherLocation.getLongitude();
        this.latitude = anotherLocation.getLatitude();
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public LatLng getLatLng(){
        return new LatLng(latitude, longitude);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLocation(Location location){
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    public double calculateDistance(Location anotherLocation){
        double distance = 7;
        //TO DO algorytm obliczania odległości między dwoma lokacjami (Klaudia)
        return distance;
    }
}
