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

        double lat1 = this.latitude;
        double lon1 = this.longitude;
        double lat2 = anotherLocation.getLatitude();
        double lon2 = anotherLocation.getLongitude();
        //TO DO algorytm obliczania odległości między dwoma lokacjami (Klaudia)

        double earthRadius = 6371;
        double latDiff = Math.toRadians(lat2-lat1);
        double lngDiff = Math.toRadians(lon2-lon1);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat1))*
                        Math.cos(Math.toRadians(lat2))* Math.sin(lngDiff /2) *
                        Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
}

