package com.example.parisroutefinder;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.*;

public class ParisMap {
    // Using an adjacency matrix to represent the map
    double[][] adjacencyMatrix;
    public Map<String, Integer> landmarkIndexMap;
    private int size; // tracks the Size of the matrix
    public WritableImage BwImage;



    public ParisMap(int initialCapacity, WritableImage image) {
        this.BwImage = image;
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
            System.out.println("Adding landmark: " + landmark.getName() + " with index " + size);
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
    public ArrayList<Street> dijkstraShortestPath(String landmark1, String landmark2, ArrayList<String> avoid) {
        ArrayList<Street> streets = new ArrayList<>();

        // Get indices of landmarks
        Integer index1 = landmarkIndexMap.get(landmark1);
        Integer index2 = landmarkIndexMap.get(landmark2);

        // Check if indices are valid
        if (index1 == null || index2 == null || index1 >= size || index2 >= size) {
            System.out.println("Invalid indices or landmarks not found.");
            return streets; // Return empty path indicating an error
        }

        double[] distances = new double[size];
        Arrays.fill(distances, Double.MAX_VALUE);
        distances[index1] = 0;

        int[] previous = new int[size];
        Arrays.fill(previous, -1);

        boolean[] visited = new boolean[size];
        for (String a : avoid){
            int indexToAvoid = landmarkIndexMap.get(a);
            visited[indexToAvoid]=true;
        }

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

        // Construct the path from end to start by following previous array
        int current = index2;
        while (previous[current] != -1) {
            int prev = previous[current];
            // Assuming landmarks are stored in a list or similar structure
            Landmark startLandmark = MainController.mainController.landmarks.get(prev);
            Landmark endLandmark = MainController.mainController.landmarks.get(current);
            streets.add(new Street("", startLandmark, endLandmark)); // Street name is empty as example
            current = prev;
        }

        return streets;
    }


    public ArrayList<Street> dijkstraShortestPathGEORGE(String landmark1, String landmark2){
        ArrayList<Street> streets = new ArrayList<>();

        // Get indices of landmarks
        Integer index1 = landmarkIndexMap.get(landmark1);
        Integer index2 = landmarkIndexMap.get(landmark2);

        if (index1 == null || index2 == null) {
            System.out.println("One of the landmarks is not in the map: " + landmark1 + ", " + landmark2);
            return streets; // Return empty list if either index is null
        }

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
    public ArrayList<Point2D> bfsShortestPath(Image BwImage, Point2D start, Point2D end){
        int wiidth = (int) BwImage.getWidth();
        int height = (int) BwImage.getHeight();
        boolean[][] visited = new boolean[wiidth][height];
        // The line `Queue<Point2D> queue = new LinkedList<>();` is creating a queue data structure using the `LinkedList`
        // implementation in Java.
        Queue<Point2D> queue = new LinkedList<>();
        Map<Point2D, Point2D>parent = new HashMap<>();
        queue.add(start);
        visited[(int)start.getX()][(int)start.getY()] = true;
        while (!queue.isEmpty()) {

            Point2D current = queue.poll();
            if(current.equals(end)){
                return contructPath(parent, current);
            }
            // initializing arrays `dx` and `dy` with values that represent the changes in x and y
            // coordinates for moving in four directions: up, left, down, and right.
            double[] dx = {-1, 0, 1, 0};
            double[] dy = {0, -1, 0, 1};
            for (int i = 0; i < dx.length; i++) {
                for (int j = 0; j < dy.length; j++) {
                    // calculating the new coordinates `(x, y)` by adding the changes in x and y
                    // directions specified by the arrays `dx` and `dy` to the current coordinates of the point `current`.
                    int x = (int) (current.getX() + dx[i]);
                    int y = (int) (current.getY() + dy[j]);
                    if (x >= 0 && x < wiidth && y >= 0 && y < height &&!visited[(int)x][(int)y] && isWhite(BwImage, x, y)) {
                        queue.add(new Point2D(x, y));
                        visited[(int)x][(int)y] = true;
                        parent.put(new Point2D(x, y), current);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    private boolean isWhite(Image bwImage, int x, int y) {
        Color color = bwImage.getPixelReader().getColor(x, y);
        return color.getBrightness() > 0.9; // A threshold to account for slight variations in color
    }

    private ArrayList<Point2D> contructPath(Map<Point2D, Point2D> parent, Point2D current) {
        LinkedList<Point2D> path = new LinkedList<>();
        for (Point2D p = current; p!= null; p = parent.get(p)){
            path.addFirst(p);
        }
       return new ArrayList<Point2D>(path);
    }
    // Method to initiate DFS and find all paths
    public List<List<String>> findAllPaths(String startLandmark, String endLandmark) {
        List<List<String>> allPaths = new ArrayList<>();
        if (landmarkIndexMap.containsKey(startLandmark) && landmarkIndexMap.containsKey(endLandmark)) {
            int startIdx = landmarkIndexMap.get(startLandmark);
            int endIdx = landmarkIndexMap.get(endLandmark);
            dfsAllPaths(startIdx, endIdx, new boolean[size], new ArrayList<>(), allPaths);
        }
        return allPaths;
    }


    private void dfsAllPaths(int current, int destination, boolean[] visited, List<Integer> path, List<List<String>> allPaths) {
        visited[current] = true;
        path.add(current);

        if (current == destination) {
            allPaths.add(convertPathToString(path));
        } else {
            for (int i = 0; i < size; i++) {
                if (!visited[i] && adjacencyMatrix[current][i] != Double.MAX_VALUE) {
                    dfsAllPaths(i, destination, visited, path, allPaths);
                }
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        visited[current] = false;
    }
    public String getLandmarkName(int index) {
        return MainController.mainController.landmarks.get(index).getName();
    }
    private List<String> convertPathToString(List<Integer> path) {
        List<String> stringPath = new ArrayList<>();
        for (int index : path) {
            stringPath.add(getLandmarkName(index));
        }
        return stringPath;
    }

    // Random path selection
    public List<List<String>> selectRandomPaths(List<List<String>> allPaths, int count) {
        List<List<String>> selectedPaths = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count && !allPaths.isEmpty(); i++) {
            selectedPaths.add(allPaths.remove(random.nextInt(allPaths.size())));
        }
        return selectedPaths;
    }
    public List<List<String>> getRandomPaths(List<List<String>> allPaths, int numberOfPaths) {
        List<List<String>> randomPaths = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < Math.min(numberOfPaths, allPaths.size()); i++) {
            randomPaths.add(allPaths.remove(rand.nextInt(allPaths.size())));
        }
        return randomPaths;
    }


}
