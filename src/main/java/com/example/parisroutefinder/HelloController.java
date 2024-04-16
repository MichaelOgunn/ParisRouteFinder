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
        Landmark l4 = new Landmark("Louvre", 303,189,10);
        Landmark l5 = new Landmark("Opera Garnier", 292,134,10);
        Landmark l6 = new Landmark("Catacombs", 282,361,10);
        Landmark l7 = new Landmark("Eiffel Tower", 121,205,10);

//        Street s1 = new Street("Avenue Kleber", 10, l2,l7);//arc de triomphe to eiffel tower
//        Street s2 = new Street("Bd de Courcelles", 20, l2,l3);//arc de triomphe to sacre-coeur
//        Street s3 = new Street("Av. des Champs", 18, l2,l4);//arc de triomphe to louvre
//        Street s4 = new Street("R. d'Amsterdam", 15, l3,l5);//sacre-coeur to opera garnier


        //Adding Landmarks to list based on where they are on the map, using index on map as key
        MainController.mainController.landmarks.add(l1);
        MainController.mainController.landmarks.add(l2);
        MainController.mainController.landmarks.add(l3);
        MainController.mainController.landmarks.add(l4);
        MainController.mainController.landmarks.add(l5);
        MainController.mainController.landmarks.add(l6);
        MainController.mainController.landmarks.add(l7);

        //Adding Landmarks to ParisMap based on where they are on the map, using index on map as key
        MainController.mainController.parisGraph=new ParisMap(7);
        MainController.mainController.parisGraph.addLandmark(l1);
        MainController.mainController.parisGraph.addLandmark(l2);
        MainController.mainController.parisGraph.addLandmark(l3);
        MainController.mainController.parisGraph.addLandmark(l4);
        MainController.mainController.parisGraph.addLandmark(l5);
        MainController.mainController.parisGraph.addLandmark(l6);
        MainController.mainController.parisGraph.addLandmark(l7);

        MainController.mainController.startLandmarks.getItems().add(l1);
        MainController.mainController.startLandmarks.getItems().add(l2);
        MainController.mainController.startLandmarks.getItems().add(l3);
        MainController.mainController.startLandmarks.getItems().add(l4);
        MainController.mainController.startLandmarks.getItems().add(l5);
        MainController.mainController.startLandmarks.getItems().add(l6);
        MainController.mainController.startLandmarks.getItems().add(l7);

        MainController.mainController.destLandmarks.getItems().add(l1);
        MainController.mainController.destLandmarks.getItems().add(l2);
        MainController.mainController.destLandmarks.getItems().add(l3);
        MainController.mainController.destLandmarks.getItems().add(l4);
        MainController.mainController.destLandmarks.getItems().add(l5);
        MainController.mainController.destLandmarks.getItems().add(l6);
        MainController.mainController.destLandmarks.getItems().add(l7);

        HelloApplication.mainStage.setScene(HelloApplication.mainS);
        MainController.mainController.parisMap = MainController.mainController.mapImageView.getImage();

        Canvas canvas = new Canvas(MainController.mainController.parisMap.getWidth(),MainController.mainController.parisMap.getHeight());
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.drawImage(MainController.mainController.parisMap, 0, 0, MainController.mainController.parisMap.getWidth(), MainController.mainController.parisMap.getHeight());
        for (Landmark l : MainController.mainController.landmarks){
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillOval(l.latitude - 5, l.longitude- 5, 10, 10);
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