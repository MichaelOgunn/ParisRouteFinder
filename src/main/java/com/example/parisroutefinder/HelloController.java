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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloController=this;
    }
}