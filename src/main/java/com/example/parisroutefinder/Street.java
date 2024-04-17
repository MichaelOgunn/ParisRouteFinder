package com.example.parisroutefinder;


public class Street {
    String name;
    double distance;
    Landmark startLandmark;
    Landmark endLandmark;

    public Street(String name, Landmark start, Landmark end) {
        this.name = name;
        this.distance = calcDistance(start.latitude,start.longitude,end.latitude,end.longitude);
        this.startLandmark = start;
        this.endLandmark = end;
    }

    public double calcDistance(double x1, double y1, double x2, double y2 ){
        return (int) Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
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

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                ", startLandmark=" + startLandmark +
                ", endLandmark=" + endLandmark +
                '}';
    }
}