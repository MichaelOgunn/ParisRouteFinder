package com.example.parisroutefinder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public static HelloController helloController;

    @FXML
    protected void mainButton() {
        //Adding Landmarks to ParisMap based on where they are on the map, using index on map as key
        MainController.mainController.parisGraph=new ParisMap(40, MainController.mainController.bAndWParis);
        Path path = Paths.get("src/main/resources/CSV/Landmark.csv");
        try {
            List<String> lines = Files.readAllLines(path);
            Map<String, Landmark> landmarksMap = new HashMap<>();

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;  // Skip empty lines

                String[] split = line.split(",", -1); // Split with -1 to include empty trailing parts
                if (split.length < 5) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String type = split[0].trim();

                if ("Landmark".equals(type) || "Junction".equals(type)) {

                    if (split.length < 5) {
                        System.out.println("Skipping incomplete landmark/junction entry on line " + (i + 1));
                        continue;
                    }

                    String name = split[1].trim().replace("\"", "");
                    int x = Integer.parseInt(split[2].trim());
                    int y = Integer.parseInt(split[3].trim());
                    int culturalValue = Integer.parseInt(split[4].trim());
                    boolean isJunction = type.equals("Junction"); // Determine if it's a junction or not


                    Landmark landmark = new Landmark(name, x, y, culturalValue, isJunction);
                    landmarksMap.put(name, landmark);
                    landmark.setIsJunction("Junction".equals(type));
                    if ("Landmark".equals(type)) {

                        MainController.mainController.startLandmarks.getItems().add(landmark.name);
                        MainController.mainController.destLandmarks.getItems().add(landmark.name);
//                        MainController.mainController.landmarks.add(landmark);
                    }
                    MainController.mainController.landmarks.add(landmark);
                    MainController.mainController.parisGraph.addLandmark(landmark);

                }
                else if ("Street".equals(type)) {
                    if (split.length < 7) {
                        System.out.println("Skipping incomplete street entry on line " + (i + 1));
                        continue;
                    }
                    String streetName = split[1].trim().replace("\"", "");
                    String startName = split[5].trim().replace("\"", "");
                    String endName = split[6].trim().replace("\"", "");

                    Landmark startLandmark = landmarksMap.get(startName);
                    Landmark endLandmark = landmarksMap.get(endName);

                    if (startLandmark != null && endLandmark != null) {
                        Street street = new Street(streetName, startLandmark, endLandmark);
                        MainController.mainController.parisGraph.addStreet(street);
                    } else {
                        System.out.println("Cannot find landmarks for street: " + streetName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        MainController.mainController.startLandmarks.getItems().add("Waypoint");




        HelloApplication.mainStage.setScene(HelloApplication.mainS);
        MainController.mainController.parisMap = MainController.mainController.mapImageView.getImage();

        Canvas canvas = new Canvas(MainController.mainController.parisMap.getWidth(),MainController.mainController.parisMap.getHeight());
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.drawImage(MainController.mainController.parisMap, 0, 0, MainController.mainController.parisMap.getWidth(), MainController.mainController.parisMap.getHeight());
        for (Landmark l : MainController.mainController.landmarks) {
            if (!(l.isJunction())) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillOval(l.latitude - 5, l.longitude - 5, 10, 10);
            }
//            else {
//                graphicsContext.setFill(Color.PURPLE);
//                graphicsContext.fillOval(l.latitude - 5, l.longitude - 5, 10, 10);//for testing
//            }
        }
        WritableImage landmarks = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, landmarks);
        MainController.mainController.mapImageView.setImage(landmarks);
        MainController.mainController.parisWithLandmarks=landmarks;

        //Black and white image for finding routes
        MainController.mainController.bAndWParis = new WritableImage((int) landmarks.getWidth(), (int) landmarks.getHeight());
        for (int y = 0; y < landmarks.getHeight(); y++)
            for (int x = 0; x < landmarks.getWidth(); x++)
                if (MainController.mainController.parisMap.getPixelReader().getColor(x,y).getBrightness()<.9)//changing routes to white, everything else to black
                    MainController.mainController.bAndWParis.getPixelWriter().setColor(x,y,Color.BLACK);
                else MainController.mainController.bAndWParis.getPixelWriter().setColor(x,y,Color.WHITE);
//                MainController.mainController.bwMapImageView.setImage(MainController.mainController.bAndWParis);
        MainController.mainController.allToAvoid.add(MainController.mainController.catacombs);
        MainController.mainController.allToAvoid.add(MainController.mainController.louvre);
        MainController.mainController.allToAvoid.add(MainController.mainController.operaGarnier);
        MainController.mainController.allToAvoid.add(MainController.mainController.sacreCouer);
        MainController.mainController.allToAvoid.add(MainController.mainController.arcDeTriomphe);
        MainController.mainController.allToAvoid.add(MainController.mainController.eiffelTower);
        MainController.mainController.allToAvoid.add(MainController.mainController.notreDam);//for avoiding landmarks
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloController=this;
    }
}