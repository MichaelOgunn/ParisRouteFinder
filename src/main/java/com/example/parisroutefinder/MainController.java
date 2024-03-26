package com.example.parisroutefinder;

import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static MainController mainController;
    public ImageView mapImageView,bfsView;
    public Image parisMap;
    public Tooltip tooltip;

    public void clickOnImage(MouseEvent e){
        double xInView = e.getX();
        double yInView = e.getY();

        double ratio = parisMap.getWidth() / mapImageView.getFitWidth();//getting ratio of image:imageview
        int xOfImage = (int) (xInView * ratio);
        int yOfImage = (int) (yInView * ratio);

        if (parisMap.getPixelReader().getColor(xOfImage,yOfImage)== Color.RED) {
            //TO FILL
        } else if (parisMap.getPixelReader().getColor(xOfImage,yOfImage)== Color.BLUE){
            //TO FILL
        }

        System.out.println(xOfImage+" "+ yOfImage);
    }


    public void removeToolTip() {
        if (tooltip!=null) tooltip.hide();//hide after mouse moved
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainController=this;
    }
}
