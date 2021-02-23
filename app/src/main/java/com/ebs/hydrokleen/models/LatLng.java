package com.ebs.hydrokleen.models;

public class LatLng {

    public String Lat;
    public String Lng;

    public LatLng(){

    }

    public LatLng(String lat, String lng) {
        Lat = lat;
        Lng = lng;
    }

    public String getLat() {
        return Lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public void setLng(String lng) {
        Lng = lng;
    }
}
