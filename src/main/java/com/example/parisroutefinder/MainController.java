package com.example.parisroutefinder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static MainController mainController;
    @FXML
    public ImageView mapImageView;
    @FXML
    public ImageView bwMapImageView;
    @FXML
    public TextField historicVal, routeNumber;
    @FXML
    public RadioButton waypoint;
    @FXML
    public ChoiceBox<String> startLandmarks,destLandmarks;
    @FXML
    public CheckBox louvre,sacreCouer,eiffelTower,notreDam,arcDeTriomphe,operaGarnier,catacombs;
    public ArrayList<CheckBox> allToAvoid = new ArrayList<>();
    public Point2D lastClick  ;
    public Image parisMap,parisWithLandmarks;
    public WritableImage bAndWParis;
    public Tooltip tooltip;
    public List<Landmark> landmarks = new ArrayList<>();
    public ParisMap parisGraph;


    public void clickOnImageOLd(MouseEvent e){
        double xInView = e.getX();
        double yInView = e.getY();

        double ratio = parisMap.getWidth() / mapImageView.getFitWidth();//getting ratio of image:imageview
        int xOfImage = (int) (xInView * ratio);
        int yOfImage = (int) (yInView * ratio);
        if (!waypoint.isSelected()) {
            addToolTip(e, xOfImage, yOfImage);
            System.out.println(xOfImage+" "+ yOfImage);
        } else {
            Canvas canvas = new Canvas(parisWithLandmarks.getWidth(),parisWithLandmarks.getHeight());
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.drawImage(parisWithLandmarks, 0, 0, parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());

            graphicsContext.setFill(Color.BLUE);
            graphicsContext.fillOval(xOfImage - 5, yOfImage- 5, 10, 10);

            WritableImage waypointed = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, waypointed);
            MainController.mainController.mapImageView.setImage(waypointed);
        }
    }
    public void clickOnImage(MouseEvent e) {
        double xInView = e.getX();
        double yInView = e.getY();

        double ratio = parisMap.getWidth() / mapImageView.getFitWidth();
        int xOfImage = (int) (xInView * ratio);
        int yOfImage = (int) (yInView * ratio);

        // Store the last clicked waypoint
        lastClick = new Point2D(xOfImage, yOfImage);

        if (!waypoint.isSelected()) {
            addToolTip(e, xOfImage, yOfImage);
            System.out.println(xOfImage + " " + yOfImage);
        } else {
            Canvas canvas = new Canvas(parisWithLandmarks.getWidth(),parisWithLandmarks.getHeight());
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.drawImage(parisWithLandmarks, 0, 0, parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());

            graphicsContext.setFill(Color.BLUE);
            graphicsContext.fillOval(xOfImage - 5, yOfImage- 5, 10, 10);

            WritableImage waypointed = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, waypointed);
            MainController.mainController.mapImageView.setImage(waypointed);
        }
    }
    private Landmark tempWaypoint;
    private List<Street> tempStreets = new ArrayList<>();

    @FXML
    public void dijkstraShortestPath() {
        mapImageView.setImage(parisWithLandmarks);
        if (startLandmarks.getValue().equals("Waypoint") && destLandmarks.getValue() != null) {
            Point2D start = lastClick;
            if (waypoint.isSelected()) {
                // Create and add a temporary waypoint
                tempWaypoint = new Landmark("Waypoint", start.getX(), start.getY(), 10, false);
                landmarks.add(tempWaypoint);
                parisGraph.addLandmark(tempWaypoint);

                // Find two closest landmarks and create streets connecting to the waypoint
                Landmark closestLandmark1 = findClosestLandmark(start.getX(), start.getY());
                Landmark closestLandmark2 = findAnotherClosestLandmark(start.getX(), start.getY(), closestLandmark1);

                Street s1 = new Street("waypointLm to dest", tempWaypoint, closestLandmark1);
                Street s2 = new Street("waypointLm to dest2", tempWaypoint, closestLandmark2);

                // Add these streets to both the graph and the temporary streets list
                parisGraph.addStreet(s1);
                parisGraph.addStreet(s2);
                tempStreets.add(s1);
                tempStreets.add(s2);
                ArrayList<String> avoid = new ArrayList<>();
                for (CheckBox a : allToAvoid)
                    if (a.isSelected()) avoid.add(a.getText());
                ArrayList<Street> streets = parisGraph.dijkstraShortestPath(startLandmarks.getValue(),destLandmarks.getValue(),avoid);
                Canvas canvas = new Canvas(parisWithLandmarks.getWidth(),parisWithLandmarks.getHeight());
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.drawImage(parisWithLandmarks,0,0,parisWithLandmarks.getWidth(),parisWithLandmarks.getHeight());
                for (Street s : streets)
                    gc.strokeLine(start.getX(),start.getY(),s.endLandmark.latitude,s.endLandmark.longitude);
                gc.setFill(Color.BLUE);
                gc.fillOval(lastClick.getX()-5, lastClick.getY()- 5, 10, 10);

                WritableImage imageWithPath = new WritableImage((int) parisMap.getWidth(), (int) parisMap.getHeight());
                canvas.snapshot(null, imageWithPath);
                mapImageView.setImage(imageWithPath);
//                resetWaypoints();
            }
        }
        if (startLandmarks.getValue() != null && destLandmarks.getValue() != null) {
            ArrayList<String> avoid = new ArrayList<>();
            for (CheckBox a : allToAvoid)
                if (a.isSelected()) avoid.add(a.getText());
            ArrayList<Street> streets = parisGraph.dijkstraShortestPath(startLandmarks.getValue(),destLandmarks.getValue(),avoid);
            Canvas canvas = new Canvas(parisWithLandmarks.getWidth(),parisWithLandmarks.getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(parisWithLandmarks,0,0,parisWithLandmarks.getWidth(),parisWithLandmarks.getHeight());
            for (Street s : streets)
                gc.strokeLine(s.startLandmark.latitude,s.startLandmark.longitude,s.endLandmark.latitude,s.endLandmark.longitude);
            WritableImage imageWithPath = new WritableImage((int) parisMap.getWidth(), (int) parisMap.getHeight());
            canvas.snapshot(null, imageWithPath);
            mapImageView.setImage(imageWithPath);
        }
    }
    public void resetWaypoints() {

        // Remove temporary streets
        for (Street street : tempStreets) {
            parisGraph.removeStreet(street);
        }
        tempStreets.clear();

        // Remove temporary waypoint
        if (tempWaypoint != null) {
            parisGraph.removLandMark(tempWaypoint);
            landmarks.remove(tempWaypoint);
            tempWaypoint = null;
        }
        lastClick = null;
        waypoint.setSelected(false);
        waypointDeSelected();
        System.out.println("resetWaypoints");
    }
    public void dijkstraWithWaypoint(String waypointName, double waypointX, double waypointY, String destination) {
        // Create and add the waypoint
        Landmark waypoint = new Landmark(waypointName, waypointX, waypointY, 0, false);
        System.out.println("Before adding waypoint: " + parisGraph.landmarkIndexMap);
        landmarks.add(waypoint);
        parisGraph.addLandmark(waypoint);
        System.out.println("After adding waypoint: " + parisGraph.landmarkIndexMap);

        // Find two closest landmarks
        Landmark closestLandmark1 = findClosestLandmark(waypointX, waypointY);
        Landmark closestLandmark2 = findAnotherClosestLandmark(waypointX, waypointY, closestLandmark1);

        if (closestLandmark1 != null) {
            Street toLandmark1 = new Street(waypointName + " to " + closestLandmark1.getName(), waypoint, closestLandmark1);
            parisGraph.addStreet(toLandmark1);
        } else {
            System.out.println("No close landmark found for connection 1");
        }

        if (closestLandmark2 != null) {
            Street toLandmark2 = new Street(waypointName + " to " + closestLandmark2.getName(), waypoint, closestLandmark2);
            parisGraph.addStreet(toLandmark2);
        } else {
            System.out.println("No close landmark found for connection 2");
        }

        // Validate the connections before running Dijkstra's algorithm
        if (!parisGraph.landmarkIndexMap.containsKey(destination)) {
            System.out.println("Destination not found: " + destination);
            return;
        }

        ArrayList<String> avoid = new ArrayList<>();
        System.out.println("Avoid List: " + avoid);

        ArrayList<Street> path = parisGraph.dijkstraShortestPath(waypointName, destination, avoid);

        if (path.isEmpty()) {
            System.out.println("No path found from " + waypointName + " to " + destination);
        }

        // Visualize the path
        Canvas canvas = new Canvas(parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(parisWithLandmarks, 0, 0, parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());

        for (Street s : path) {
            gc.strokeLine(s.startLandmark.latitude, s.startLandmark.longitude, s.endLandmark.latitude, s.endLandmark.longitude);
        }

        WritableImage imageWithPath = new WritableImage((int) parisMap.getWidth(), (int) parisMap.getHeight());
        canvas.snapshot(null, imageWithPath);
        mapImageView.setImage(imageWithPath);
    }

    private Landmark findClosestLandmark(double x, double y) {
        Landmark closestLandmark = null;
        double minDistance = Double.MAX_VALUE;

        // Loop through all landmarks in the main graph controller to find the closest
        for (Landmark landmark : MainController.mainController.landmarks) {
            double distance = calculateDistance(x, y, landmark.latitude, landmark.longitude);
            if (distance < minDistance) {
                minDistance = distance;
                closestLandmark = landmark;
            }
        }

        return closestLandmark;
    }

    private Landmark findAnotherClosestLandmark(double x, double y, Landmark exclude) {
        Landmark secondClosestLandmark = null;
        double minDistance = Double.MAX_VALUE;

        // Loop through all landmarks and find the closest that isn't the excluded one
        for (Landmark landmark : MainController.mainController.landmarks) {
            if (landmark.equals(exclude)) continue;  // Skip the excluded landmark

            double distance = calculateDistance(x, y, landmark.latitude, landmark.longitude);
            if (distance < minDistance) {
                minDistance = distance;
                secondClosestLandmark = landmark;
            }
        }

        return secondClosestLandmark;
    }
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }


    public void bfsShortestPath() {
        if(startLandmarks.getValue().equals("Waypoint") && destLandmarks.getValue()!=null && waypoint.isSelected()) {
            String destLandmark = destLandmarks.getValue();
            Point2D start = lastClick;
            Point2D end = landmarkPoint(destLandmark);
            if(start!=null && end!=null) {
                ArrayList<Point2D> path = parisGraph.bfsShortestPath(bAndWParis, start, end);
                Canvas canvas = new Canvas(parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.drawImage(parisWithLandmarks, 0, 0, parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());
                if (path.size() > 1) {

                    for (int i = 0; i < path.size() - 1; i++) {
                        Point2D p1 = path.get(i);
                        Point2D p2 = path.get(i + 1);
                        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    }
                }
                WritableImage imageWithPath = new WritableImage((int) parisMap.getWidth(), (int) parisMap.getHeight());
                canvas.snapshot(null, imageWithPath);
                mapImageView.setImage(imageWithPath);
            }
        }
        if (startLandmarks.getValue() != null && destLandmarks.getValue() != null) {
            String startLandmark = startLandmarks.getValue();
            String destLandmark = destLandmarks.getValue();
            Point2D start = landmarkPoint(startLandmark);
            Point2D end = landmarkPoint(destLandmark);
            if(start!=null && end!=null) {
                ArrayList<Point2D> path = parisGraph.bfsShortestPath(bAndWParis,start, end);
                Canvas canvas = new Canvas(parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.drawImage(parisWithLandmarks, 0, 0, parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());
                if(path.size()>1) {

                    for (int i = 0; i < path.size() -1; i++) {
                        Point2D p1= path.get(i);
                        Point2D p2= path.get(i+1);
                        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    }
                }
                WritableImage imageWithPath = new WritableImage((int) parisMap.getWidth(), (int) parisMap.getHeight());
                canvas.snapshot(null, imageWithPath);
                mapImageView.setImage(imageWithPath);
            }
            else {
                System.out.println("Landmark not found");
            }
        }


    }
   public void multipleDFS(){
        if(startLandmarks.getValue()!=null && destLandmarks.getValue()!=null) {
            List<List<String>> allPaths = parisGraph.findAllPaths(startLandmarks.getValue(),destLandmarks.getValue());
            int routes = Integer.parseInt(routeNumber.getText());
            List<List<String>> selectedPaths = parisGraph.selectRandomPaths(allPaths,routes);
            drawPaths(selectedPaths);



        }
   }
    private void drawPaths(List<List<String>> paths) {
        Canvas canvas = new Canvas(parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(parisWithLandmarks, 0, 0, parisWithLandmarks.getWidth(), parisWithLandmarks.getHeight());

        Random rand = new Random();
        for (List<String> path : paths) {
            // Generate random color for each path
            Color randomColor = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            gc.setStroke(randomColor);
            gc.setLineWidth(2);

            for (int i = 0; i < path.size() - 1; i++) {
                String startLandmarkName = path.get(i);
                String endLandmarkName = path.get(i + 1);
                Point2D start = landmarkPoint(startLandmarkName);
                Point2D end = landmarkPoint(endLandmarkName);
                if (start != null && end != null) {
                    gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
                }
            }
        }
        mapImageView.setImage(canvas.snapshot(null, null)); // Update the ImageView with the new canvas
    }


    private Point2D landmarkPoint(String landmarkName) {
        Landmark landmark = getLandmarkByName(landmarkName);
        if (landmark!=null) {
            return new Point2D(landmark.latitude,landmark.longitude);
        }
        return null;
    }

    public Landmark getLandmarkByName(String landmarkName) {
        for (Landmark landmark : landmarks) {
            if (landmark.getName().equals(landmarkName)) {
                return landmark;
            }
        }
        return null;
    }

    public void addToolTip(MouseEvent e, int latitude, int longitude) {
        for (Landmark l :landmarks) {
            if ((l.latitude<=latitude+10 && l.latitude>=latitude-10) && (l.longitude<=longitude+10 && l.longitude>=longitude-10)
                && !(l.isJunction())) {
                tooltip = new Tooltip("Name: " + l.getName() + '\n');
                break;
            }
        }
        if (tooltip!=null) tooltip.show(mapImageView, e.getScreenX(),e.getScreenY());
    }

    public void removeToolTip() {
        if (tooltip!=null) {
            tooltip.hide();//hide after mouse moved
            tooltip=null;
        }
    }

    public void waypointDeSelected(){
        if (!waypoint.isSelected()){
            mapImageView.setImage(parisWithLandmarks);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainController=this;

    }
}
