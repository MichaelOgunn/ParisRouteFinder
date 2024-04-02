
package com.example.parisroutefinder;

import java.util.HashMap;
import java.util.Map;

public class ParisMap {
    // Using an adjacency matrix to represent the map
    private double[][] adjacencyMatrix;
    private Map<String, Integer> landmarkIndexMap;
    private int size; // tracks the Size of the matrix


    public ParisMap(int initialCapacity) {
        adjacencyMatrix = new double[initialCapacity][initialCapacity];
        //initializing distances to indicate that there is no route
        for (int i = 0; i <initialCapacity ; i++) {
            for (int j = 0; j < initialCapacity; j++) {
                if (i == j) {
                    adjacencyMatrix[i][j] = 0; // 0 means the distance from a node to itself is 0
                }
                else {
                    adjacencyMatrix[i][j] = Double.MAX_VALUE; // Double.MAX_VALUE means there is no route
                }
            }
        }
        landmarkIndexMap = new HashMap<>();
        size = 0;
    }
    // Methods to add/remove landmarks and streets, find routes, etc.
    public void addLandmark(Landmark landmark) {
        if (!landmarkIndexMap.containsKey(landmark.getName())) {
            landmarkIndexMap.put(landmark.getName(), size);
            adjacencyMatrix[size][size] = 0;
            adjacencyMatrix[size][landmarkIndexMap.get(landmark.getName())] = 0;
            adjacencyMatrix[landmarkIndexMap.get(landmark.getName())][size] = 0;
            size++;
        }
    }
    public void removLandMark(Landmark landmark){
        if(landmarkIndexMap.containsKey(landmark.getName())){
            int indexToRemove = landmarkIndexMap.get(landmark.getName());
            //remove the landmark from the map
            landmarkIndexMap.remove(landmark.getName());
            //remove the landmark from the adjacency matrix
            for (int i = indexToRemove+ 1; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // shifting the values in the adjacency
                    // matrix to fill the gap left by removing a landmark.
                    adjacencyMatrix[i-1][j] = adjacencyMatrix[i][j];
                }
            }
            for (int j = indexToRemove +1 ; j < size; j++) {
                for (int i = 0; i < size; i++) {
                    // shifting the values in the adjacency
                    // matrix to fill the gap left by removing a landmark.
                    adjacencyMatrix[i][j-1] = adjacencyMatrix[i][j];
                }
            }
            //update the index of the landmark in the adjacency matrix
            landmarkIndexMap.forEach((name, index) -> {
                if (index > indexToRemove) {
                    landmarkIndexMap.put(name, index - 1);
                }
            });
            //update the index of the landmark in the adjacency matrix
            size--;
        }
    }
    public void addStreet(Street street) {
        if (landmarkIndexMap.containsKey(street.getStartLandmark().getName()) && landmarkIndexMap.containsKey(street.getEndLandmark().getName())) {
            int startIndex = landmarkIndexMap.get(street.getStartLandmark().getName());
            int endIndex = landmarkIndexMap.get(street.getEndLandmark().getName());
            adjacencyMatrix[startIndex][endIndex] = street.getDistance();
            adjacencyMatrix[endIndex][startIndex] = street.getDistance();
        }
    }

}
