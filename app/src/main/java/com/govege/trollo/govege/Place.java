package com.govege.trollo.govege;


import java.io.Serializable;

/**
 * Created by Trollo on 2018-12-15.
 */

public class Place implements Serializable{

    private String name;
    private String address;
    private double rating;
    private Location location;
    private boolean openNow;

    public Place(String name, String address, double rating, Location location, boolean openNow){
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.location = location;
        this.openNow = openNow;
    }

    public String getName(){
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getRating() {
        return rating;
    }

    public Location getLocation() {
        return location;
    }

    public boolean getOpenNow() { return openNow; }
}
