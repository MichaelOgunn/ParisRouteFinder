package com.example.parisroutefinder;

import java.util.ArrayList;
import java.util.List;

public class Landmark {
    String name;
    double latitude;
    double longitude;
    int culturalValue;
    // Consider using a Map if you need to quickly lookup streets by their destination landmark.
    List<Street> adjacentStreets;
    private Boolean isJunction;

    public Landmark(String name, double latitude, double longitude, int culturalValue,boolean isJunction) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.culturalValue = culturalValue;
        this.adjacentStreets = new ArrayList<>();
        this.isJunction= isJunction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean isJunction() {
        return isJunction;
    }
    public void setIsJunction(boolean isJunction) {
        this.isJunction = isJunction;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCulturalValue() {
        return culturalValue;
    }

    public void setCulturalValue(int culturalValue) {
        this.culturalValue = culturalValue;
    }

    // Methods to add/remove adjacent streets, etc.
    public void addAdjacentStreet(Street street) {
        adjacentStreets.add(street);
    }
    public void removeAdjacentStreet(Street street) {
        adjacentStreets.remove(street);
    }

    @Override
    public String toString() {
        return "Landmark{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", culturalValue=" + culturalValue ;
    }
}