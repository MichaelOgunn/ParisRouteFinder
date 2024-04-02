package com.example.parisroutefinder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public static HelloController helloController;

    @FXML
    protected void mainButton() {
        HelloApplication.mainStage.setScene(HelloApplication.mainS);
        MainController.mainController.parisMap = MainController.mainController.mapImageView.getImage();

        //Adding Landmarks to Hashmap based on where they are on the map, using index on map as
        MainController.mainController.landmarks.put(118, new Landmark("Notre Dam", 364,238,10));
        MainController.mainController.landmarks.put(118, new Landmark("Arc de Triomphe", 122,102,10));
        MainController.mainController.landmarks.put(118, new Landmark("Sacre-Coeur", 324,8,10));
        MainController.mainController.landmarks.put(118, new Landmark("Louvre", 303,189,10));
        MainController.mainController.landmarks.put(118, new Landmark("Opera Garnier", 292,134,10));
        MainController.mainController.landmarks.put(118, new Landmark("Catacombs", 282,361,10));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloController=this;
    }
}