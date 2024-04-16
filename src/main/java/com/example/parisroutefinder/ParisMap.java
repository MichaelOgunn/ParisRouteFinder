package com.example.parisroutefinder;

import java.util.ArrayList;
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

    public ArrayList<Street> dijkstraShortestPath(String landmark1, String landmark2){
        ArrayList<Street> streets = new ArrayList<>();

        // Get indices of landmarks
        Integer index1 = landmarkIndexMap.get(landmark1);
        Integer index2 = landmarkIndexMap.get(landmark2);

        double[] distances = new double[size];
        for (int i = 0 ; i<distances.length;i++)
            distances[i]=Double.MAX_VALUE;
        distances[index1]=0;
        int[] previous = new int[size];
        for (int i = 0 ; i<previous.length;i++)
            previous[i]=-1;
        boolean[] visited = new boolean[size];

        for (int i = 0; i < size; i++) {
            int minIndex = -1;
            double minDistance = Double.MAX_VALUE;
            // Find the vertex with the smallest tentative distance
            for (int j = 0; j < size; j++) {
                if (!visited[j] && distances[j] < minDistance) {
                    minIndex = j;
                    minDistance = distances[j];
                }
            }
            if (minIndex == -1 || minIndex == index2) {
                break; // No reachable vertices left or destination reached
            }
            visited[minIndex] = true;

            // Update distances to neighbors of the current vertex
            for (int neighbor = 0; neighbor < size; neighbor++) {
                if (!visited[neighbor] && adjacencyMatrix[minIndex][neighbor] < Double.MAX_VALUE) {
                    double alt = distances[minIndex] + adjacencyMatrix[minIndex][neighbor];
                    if (alt < distances[neighbor]) {
                        distances[neighbor] = alt;
                        previous[neighbor] = minIndex;
                    }
                }
            }
        }

        int current = index2;
        while (previous[current] != -1) {
            int prev = previous[current];
            Landmark l1 = MainController.mainController.landmarks.get(prev);
            Landmark l2= MainController.mainController.landmarks.get(current);
            streets.add(new Street("", l1, l2)); // Add street to the path
            current = prev;
        }

        for (Street s : streets)
            System.out.println(s);

        return streets;
    }

}
