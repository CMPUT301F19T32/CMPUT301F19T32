package com.example.myapplication;


import java.io.Serializable;

/** Model for holding geo location information from Google Maps API.
 *  Used my records to denote a user selected location from a map.
 *
 */

public class Geolocation implements Serializable {
    private double lat;
    private double lon;

    public Geolocation(double latitude, double longitude) {
        this.lat = latitude;
        this.lon = longitude;
    }



    public double getLatitude() {
        return lat;
    }

    public void setLatitude(double latitude) {
        this.lat = latitude;
    }

    public double getLongitude() {
        return lon;
    }

    public void setLongitude(double longitude) {
        this.lon = longitude;
    }


}
