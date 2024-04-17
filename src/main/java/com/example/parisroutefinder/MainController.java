package com.example.parisroutefinder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static MainController mainController;
    @FXML
    public ImageView mapImageView;
    @FXML
    public RadioButton waypoint;
    @FXML
    public ChoiceBox<String> startLandmarks,destLandmarks;
    @FXML
    public CheckBox louvre,sacreCouer,eiffelTower,notreDam,arcDeTriomphe,operaGarnier,catacombs;
    public Image parisMap,parisWithLandmarks;
    public WritableImage bAndWParis;
    public Tooltip tooltip;
    public List<Landmark> landmarks = new ArrayList<>();
    public ParisMap parisGraph;

    public void clickOnImage(MouseEvent e){
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

    @FXML
    public void dijkstraShortestPath() {
        mapImageView.setImage(parisWithLandmarks);
        if (startLandmarks.getValue() != null && destLandmarks.getValue() != null) {
            ArrayList<Street> streets = parisGraph.dijkstraShortestPath(startLandmarks.getValue(),destLandmarks.getValue());
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

    public void addToolTip(MouseEvent e, int latitude, int longitude) {
        for (Landmark l :landmarks) {
            if ((l.latitude<=latitude+10 && l.latitude>=latitude-10) && (l.longitude<=longitude+10 && l.longitude>=longitude-10)
                && !(l instanceof Junction)) {
                tooltip = new Tooltip("Name: " + l.getName() + '\n' +
                        "Cultural Value: " + l.getCulturalValue());
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
