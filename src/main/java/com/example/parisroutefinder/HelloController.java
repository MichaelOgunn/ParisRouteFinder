package com.example.parisroutefinder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public static HelloController helloController;

    @FXML
    protected void mainButton() {
        Landmark l1 = new Landmark("Notre Dam", 364,238,10);
        Landmark l2 = new Landmark("Arc de Triomphe", 122,102,10);
        Landmark l3 = new Landmark("Sacre-Coeur", 324,8,10);
        Landmark l4 = new Landmark("Louvre", 299,187,10);
        Landmark l5 = new Landmark("Opera Garnier", 292,134,10);
        Landmark l6 = new Landmark("Catacombs", 282,361,10);
        Landmark l7 = new Landmark("Eiffel Tower", 121,205,10);

        Junction j1 = new Junction("j1", 106,192,0);
        Junction j2 = new Junction("j2", 123,177,0);
        Junction j3 = new Junction("j3", 150,165,0);
        Junction j4 = new Junction("j4", 150,117,0);

        Junction j5 = new Junction("j5", 240, 159, 0);
        Junction j6 = new Junction("j6", 233, 169, 0);
        Junction j7 = new Junction("j7", 294, 197, 0);

        Street s1 = new Street("eiffelToj1",  l7,j1);
        Street s2 = new Street("j1Toj2",  j1,j2);
        Street s3 = new Street("j2Toj3",  j2,j3);
        Street s4 = new Street("j3Toj4",  j3,j4);
        Street s5 = new Street("j4ToArcDeTriomphe",  j4,l2);
        Street s6 = new Street("j4Toj5",j4,j5);
        Street s7 = new Street("j5Toj6",j5,j6);
        Street s8 = new Street("j6Toj7",j6,j7);
        Street s9 = new Street("j7ToLouvre",j7,l4);

        //Adding Landmarks to list based on where they are on the map, using index on map as key
        MainController.mainController.landmarks.add(l1);
        MainController.mainController.landmarks.add(l2);
        MainController.mainController.landmarks.add(l3);
        MainController.mainController.landmarks.add(l4);
        MainController.mainController.landmarks.add(l5);
        MainController.mainController.landmarks.add(l6);
        MainController.mainController.landmarks.add(l7);

        MainController.mainController.landmarks.add(j2);
        MainController.mainController.landmarks.add(j1);
        MainController.mainController.landmarks.add(j3);
        MainController.mainController.landmarks.add(j4);
        MainController.mainController.landmarks.add(j5);
        MainController.mainController.landmarks.add(j6);
        MainController.mainController.landmarks.add(j7);

        //Adding Landmarks to ParisMap based on where they are on the map, using index on map as key
        MainController.mainController.parisGraph=new ParisMap(14);
        MainController.mainController.parisGraph.addLandmark(l1);
        MainController.mainController.parisGraph.addLandmark(l2);
        MainController.mainController.parisGraph.addLandmark(l3);
        MainController.mainController.parisGraph.addLandmark(l4);
        MainController.mainController.parisGraph.addLandmark(l5);
        MainController.mainController.parisGraph.addLandmark(l6);
        MainController.mainController.parisGraph.addLandmark(l7);

        MainController.mainController.parisGraph.addLandmark(j2);
        MainController.mainController.parisGraph.addLandmark(j1);
        MainController.mainController.parisGraph.addLandmark(j3);
        MainController.mainController.parisGraph.addLandmark(j4);
        MainController.mainController.parisGraph.addLandmark(j5);
        MainController.mainController.parisGraph.addLandmark(j6);
        MainController.mainController.parisGraph.addLandmark(j7);

        MainController.mainController.parisGraph.addStreet(s1);
        MainController.mainController.parisGraph.addStreet(s2);
        MainController.mainController.parisGraph.addStreet(s3);
        MainController.mainController.parisGraph.addStreet(s4);
        MainController.mainController.parisGraph.addStreet(s5);
        MainController.mainController.parisGraph.addStreet(s6);
        MainController.mainController.parisGraph.addStreet(s7);
        MainController.mainController.parisGraph.addStreet(s8);
        MainController.mainController.parisGraph.addStreet(s9);

        MainController.mainController.startLandmarks.getItems().add(l1.name);
        MainController.mainController.startLandmarks.getItems().add(l2.name);
        MainController.mainController.startLandmarks.getItems().add(l3.name);
        MainController.mainController.startLandmarks.getItems().add(l4.name);
        MainController.mainController.startLandmarks.getItems().add(l5.name);
        MainController.mainController.startLandmarks.getItems().add(l6.name);
        MainController.mainController.startLandmarks.getItems().add(l7.name);
        MainController.mainController.startLandmarks.getItems().add("Waypoint");

        MainController.mainController.destLandmarks.getItems().add(l1.name);
        MainController.mainController.destLandmarks.getItems().add(l2.name);
        MainController.mainController.destLandmarks.getItems().add(l3.name);
        MainController.mainController.destLandmarks.getItems().add(l4.name);
        MainController.mainController.destLandmarks.getItems().add(l5.name);
        MainController.mainController.destLandmarks.getItems().add(l6.name);
        MainController.mainController.destLandmarks.getItems().add(l7.name);
        MainController.mainController.destLandmarks.getItems().add("Waypoint");

        HelloApplication.mainStage.setScene(HelloApplication.mainS);
        MainController.mainController.parisMap = MainController.mainController.mapImageView.getImage();

        Canvas canvas = new Canvas(MainController.mainController.parisMap.getWidth(),MainController.mainController.parisMap.getHeight());
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.drawImage(MainController.mainController.parisMap, 0, 0, MainController.mainController.parisMap.getWidth(), MainController.mainController.parisMap.getHeight());
        for (Landmark l : MainController.mainController.landmarks) {
            if (!(l instanceof Junction)) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillOval(l.latitude - 5, l.longitude - 5, 10, 10);
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloController=this;
    }
}