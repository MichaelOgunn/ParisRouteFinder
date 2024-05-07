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
        MainController.mainController.parisGraph=new ParisMap(37, MainController.mainController.bAndWParis);
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


        //Adding Landmarks to list based on where they are on the map, using index on map as key
//        MainController.mainController.landmarks.add(l1);
//        MainController.mainController.landmarks.add(l2);
//        MainController.mainController.landmarks.add(l3);
//        MainController.mainController.landmarks.add(l4);
//        MainController.mainController.landmarks.add(l5);
//        MainController.mainController.landmarks.add(l6);
//        MainController.mainController.landmarks.add(l7);
//
//        MainController.mainController.landmarks.add(j2);
//        MainController.mainController.landmarks.add(j1);
//        MainController.mainController.landmarks.add(j3);
//        MainController.mainController.landmarks.add(j4);
//        MainController.mainController.landmarks.add(j5);
//        MainController.mainController.landmarks.add(j6);
//        MainController.mainController.landmarks.add(j7);
//        MainController.mainController.landmarks.add(j8);
//        MainController.mainController.landmarks.add(j9);
//        MainController.mainController.landmarks.add(j10);
//        MainController.mainController.landmarks.add(j11);
//        MainController.mainController.landmarks.add(j12);
//        MainController.mainController.landmarks.add(j13);
//        MainController.mainController.landmarks.add(j14);
//        MainController.mainController.landmarks.add(j15);
//        MainController.mainController.landmarks.add(j16);
//        MainController.mainController.landmarks.add(j17);
//        MainController.mainController.landmarks.add(j18);
//        MainController.mainController.landmarks.add(j19);
//        MainController.mainController.landmarks.add(j20);
//        MainController.mainController.landmarks.add(j21);
//        MainController.mainController.landmarks.add(j22);
//        MainController.mainController.landmarks.add(j23);
//        MainController.mainController.landmarks.add(j24);
//        MainController.mainController.landmarks.add(j25);
//        MainController.mainController.landmarks.add(j26);
//        MainController.mainController.landmarks.add(j27);
//        MainController.mainController.landmarks.add(j28);


//        MainController.mainController.parisGraph.addLandmark(l1);
//        MainController.mainController.parisGraph.addLandmark(l2);
//        MainController.mainController.parisGraph.addLandmark(l3);
//        MainController.mainController.parisGraph.addLandmark(l4);
//        MainController.mainController.parisGraph.addLandmark(l5);
//        MainController.mainController.parisGraph.addLandmark(l6);
//        MainController.mainController.parisGraph.addLandmark(l7);
//
//        MainController.mainController.parisGraph.addLandmark(j2);
//        MainController.mainController.parisGraph.addLandmark(j1);
//        MainController.mainController.parisGraph.addLandmark(j3);
//        MainController.mainController.parisGraph.addLandmark(j4);
//        MainController.mainController.parisGraph.addLandmark(j5);
//        MainController.mainController.parisGraph.addLandmark(j6);
//        MainController.mainController.parisGraph.addLandmark(j7);
//        MainController.mainController.parisGraph.addLandmark(j8);
//        MainController.mainController.parisGraph.addLandmark(j9);
//        MainController.mainController.parisGraph.addLandmark(j10);
//        MainController.mainController.parisGraph.addLandmark(j11);
//        MainController.mainController.parisGraph.addLandmark(j12);
//        MainController.mainController.parisGraph.addLandmark(j13);
//        MainController.mainController.parisGraph.addLandmark(j14);
//        MainController.mainController.parisGraph.addLandmark(j15);
//        MainController.mainController.parisGraph.addLandmark(j16);
//        MainController.mainController.parisGraph.addLandmark(j17);
//        MainController.mainController.parisGraph.addLandmark(j18);
//        MainController.mainController.parisGraph.addLandmark(j19);
//        MainController.mainController.parisGraph.addLandmark(j20);
//        MainController.mainController.parisGraph.addLandmark(j21);
//        MainController.mainController.parisGraph.addLandmark(j22);
//        MainController.mainController.parisGraph.addLandmark(j23);
//        MainController.mainController.parisGraph.addLandmark(j24);
//        MainController.mainController.parisGraph.addLandmark(j25);
//        MainController.mainController.parisGraph.addLandmark(j26);
//        MainController.mainController.parisGraph.addLandmark(j27);
//        MainController.mainController.parisGraph.addLandmark(j28);
//
//        MainController.mainController.parisGraph.addStreet(s1);
//        MainController.mainController.parisGraph.addStreet(s2);
//        MainController.mainController.parisGraph.addStreet(s3);
//        MainController.mainController.parisGraph.addStreet(s4);
//        MainController.mainController.parisGraph.addStreet(s5);
//        MainController.mainController.parisGraph.addStreet(s6);
//        MainController.mainController.parisGraph.addStreet(s7);
//        MainController.mainController.parisGraph.addStreet(s8);
//        MainController.mainController.parisGraph.addStreet(s9);
//        MainController.mainController.parisGraph.addStreet(s10);
//        MainController.mainController.parisGraph.addStreet(s11);
//        MainController.mainController.parisGraph.addStreet(s12);
//        MainController.mainController.parisGraph.addStreet(s13);
//        MainController.mainController.parisGraph.addStreet(s14);
//        MainController.mainController.parisGraph.addStreet(s15);
//        MainController.mainController.parisGraph.addStreet(s16);
//        MainController.mainController.parisGraph.addStreet(s17);
//        MainController.mainController.parisGraph.addStreet(s18);
//        MainController.mainController.parisGraph.addStreet(s19);
//        MainController.mainController.parisGraph.addStreet(s20);
//        MainController.mainController.parisGraph.addStreet(s21);
//        MainController.mainController.parisGraph.addStreet(s22);
//        MainController.mainController.parisGraph.addStreet(s23);
//        MainController.mainController.parisGraph.addStreet(s24);
//        MainController.mainController.parisGraph.addStreet(s25);
//        MainController.mainController.parisGraph.addStreet(s26);
//        MainController.mainController.parisGraph.addStreet(s27);
//        MainController.mainController.parisGraph.addStreet(s28);
//        MainController.mainController.parisGraph.addStreet(s29);
//        MainController.mainController.parisGraph.addStreet(s30);
//        MainController.mainController.parisGraph.addStreet(s31);
//        MainController.mainController.parisGraph.addStreet(s32);
//        MainController.mainController.parisGraph.addStreet(s33);
//        MainController.mainController.parisGraph.addStreet(s34);
//        MainController.mainController.parisGraph.addStreet(s35);
//        MainController.mainController.parisGraph.addStreet(s36);
//        MainController.mainController.parisGraph.addStreet(s37);
//        MainController.mainController.parisGraph.addStreet(s38);
//        MainController.mainController.parisGraph.addStreet(s39);
//        MainController.mainController.parisGraph.addStreet(s40);
//        MainController.mainController.parisGraph.addStreet(s41);

//        MainController.mainController.startLandmarks.getItems().add(l1.name);
//        MainController.mainController.startLandmarks.getItems().add(l2.name);
//        MainController.mainController.startLandmarks.getItems().add(l3.name);
//        MainController.mainController.startLandmarks.getItems().add(l4.name);
//        MainController.mainController.startLandmarks.getItems().add(l5.name);
//        MainController.mainController.startLandmarks.getItems().add(l6.name);
//        MainController.mainController.startLandmarks.getItems().add(l7.name);
        MainController.mainController.startLandmarks.getItems().add("Waypoint");

//        MainController.mainController.destLandmarks.getItems().add(l1.name);
//        MainController.mainController.destLandmarks.getItems().add(l2.name);
//        MainController.mainController.destLandmarks.getItems().add(l3.name);
//        MainController.mainController.destLandmarks.getItems().add(l4.name);
//        MainController.mainController.destLandmarks.getItems().add(l5.name);
//        MainController.mainController.destLandmarks.getItems().add(l6.name);
//        MainController.mainController.destLandmarks.getItems().add(l7.name);
//        MainController.mainController.destLandmarks.getItems().add("Waypoint");

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
            else {
                graphicsContext.setFill(Color.PURPLE);
                graphicsContext.fillOval(l.latitude - 5, l.longitude - 5, 10, 10);//for testing
            }
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

    }
//    public Map<String, Landmark> createLandmarksFromCSV(String pathToCsv) throws IOException {
//        Map<String, Landmark> landmarks = new HashMap<>();
//        List<String> lines = Files.readAllLines(Paths.get(pathToCsv));
//
//        for (String line : lines) {
//            // Assuming each line is like "Notre Dam",364,238,10
//            // Remove quotes and trim whitespace
//            line = line.replace("\"", "").trim();
//            String[] parts = line.split(",");
//            String name = parts[0];
//            int x = Integer.parseInt(parts[1].trim());
//            int y = Integer.parseInt(parts[2].trim());
//            int culturalValue = Integer.parseInt(parts[3].trim());
//
//            // Create the landmark and add it to the map
//            Landmark landmark = new Landmark(name, x, y, culturalValue);
//            landmarks.put(name, landmark);
//        }
//        return landmarks;
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloController=this;
    }
}