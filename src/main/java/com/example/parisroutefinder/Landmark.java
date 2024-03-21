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

    // Methods to add/remove adjacent streets, etc.
}
