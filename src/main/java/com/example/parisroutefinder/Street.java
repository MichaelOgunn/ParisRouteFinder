package com.example.parisroutefinder;


public class Street {
    String name;
    double distance;
    Landmark startLandmark;
    Landmark endLandmark;

    public Street(String name, double distance, Landmark start, Landmark end) {
        this.name = name;
        this.distance = distance;
        this.startLandmark = start;
        this.endLandmark = end;
    }
}
