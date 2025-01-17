package com.example.myapplication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoLocationTest {

    private Geolocation createGeolocation(){

        Geolocation geolocation = new Geolocation(53.484310, -113.506133);

        return geolocation;
    }

    @Test

    void testGeolocation(){
        Geolocation geolocation = createGeolocation();
        assertEquals(geolocation.getLatitude(),53.484310);
        geolocation.setLatitude(53.484566);
        assertEquals(geolocation.getLatitude(),53.484566);

        assertEquals(geolocation.getLongitude(),-113.506133);
        geolocation.setLongitude(-113.506244);
        assertEquals(geolocation.getLongitude(),-113.506244);

    }


}
