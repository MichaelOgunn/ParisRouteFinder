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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Landmark getStartLandmark() {
        return startLandmark;
    }

    public void setStartLandmark(Landmark startLandmark) {
        this.startLandmark = startLandmark;
    }

    public Landmark getEndLandmark() {
        return endLandmark;
    }

    public void setEndLandmark(Landmark endLandmark) {
        this.endLandmark = endLandmark;
    }
}