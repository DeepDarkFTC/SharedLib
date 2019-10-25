package com.example.sharedlib.Object;

import com.google.android.gms.maps.model.LatLng;

public class Library_forMapView {
    private String name;
    private int crowded;
    private int picture;
    private float distance;
    private LatLng location;

    public Library_forMapView(String name, int crowded, int picture, float distance, LatLng location) {
        this.name = name;
        this.crowded = crowded;
        this.picture = picture;
        this.distance = distance;
        this.location = location;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCrowded() {
        return crowded;
    }

    public void setCrowded(int crowded) {
        this.crowded = crowded;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
