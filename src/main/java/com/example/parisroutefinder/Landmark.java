
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

    public Landmark(String name, double latitude, double longitude, int culturalValue) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.culturalValue = culturalValue;
        this.adjacentStreets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
