package com.example.parisroutefinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Scene titleS,mainS;
    public static Stage mainStage;
    public static Scene changeScene(String file, int x, int y) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(file));
        return new Scene(fxmlLoader.load(), x, y);
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage=stage;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        titleS = new Scene(fxmlLoader.load(), 398, 400);
        mainS=changeScene("main-view.fxml",900,600);
        stage.setTitle("Paris Route Finder");
        stage.setScene(titleS);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}