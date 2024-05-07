package com.example.parisroutefinder;

import com.example.parisroutefinder.Landmark;
import com.example.parisroutefinder.ParisMap;
import com.example.parisroutefinder.Street;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class ParisMapTest {

    private ParisMap parisMap;

    @BeforeEach
    void setUp() {
        // You should set up a WritableImage with proper dimensions for your test or mock it if necessary.
        WritableImage image = new WritableImage(100, 100);
        parisMap = new ParisMap(10, image);
        HelloController.helloController= new HelloController();
        MainController.mainController = new MainController();
    }

    @Test
    void addLandmarkShouldAddLandmark() {
        Landmark landmark = new Landmark("Eiffel Tower", 0.0, 0.0, 10,true);
        parisMap.addLandmark(landmark);
        // Assuming getLandmarkName(int index) method is public or you have a method to get landmarks
        Assertions.assertTrue( parisMap.landmarkIndexMap.containsKey("Eiffel Tower"),"Eiffel Tower");
    }

    @Test
    void removeLandmarkShouldRemoveLandmark() {
        Landmark landmark = new Landmark("Eiffel Tower", 0.0, 0.0, 10,false);
        parisMap.addLandmark(landmark);
        parisMap.removLandMark(landmark);
        Assertions.assertFalse(parisMap.landmarkIndexMap.containsKey("Eiffel Tower"));
    }

    @Test
    void addStreetShouldAddStreet() {
        Landmark start = new Landmark("Louvre", 0.0, 0.0, 10,false);
        Landmark end = new Landmark("Notre Dame", 1.0, 1.0, 10,false);
        parisMap.addLandmark(start);
        parisMap.addLandmark(end);

        Street street = new Street("Louvre-Notre Dame", start, end);
        parisMap.addStreet(street);

        int startIdx = parisMap.landmarkIndexMap.get(start.getName());
        int endIdx = parisMap.landmarkIndexMap.get(end.getName());
        Assertions.assertTrue(parisMap.adjacencyMatrix[startIdx][endIdx] != Double.MAX_VALUE);
        Assertions.assertTrue(parisMap.adjacencyMatrix[endIdx][startIdx] != Double.MAX_VALUE);
    }

    @Test
    void findAllPathsShouldFindPaths() {
        MainController.mainController= new MainController();
        HelloController.helloController= new HelloController();
        Landmark start = new Landmark("Louvre", 0.0, 0.0, 10,false);
        Landmark end = new Landmark("Notre Dame", 1.0, 1.0, 10,false);
        parisMap.addLandmark(start);
        parisMap.addLandmark(end);
            MainController.mainController.landmarks.add(start);
        MainController.mainController.landmarks.add(end);
        Street street = new Street("Louvre-Notre Dame", start, end);
        parisMap.addStreet(street);

        List<List<String>> paths = parisMap.findAllPaths("Louvre", "Notre Dame");
        Assertions.assertFalse(paths.isEmpty());
    }

    @Test
    void getRandomPathsShouldReturnSpecifiedNumberOfPaths() {
        // Assume findAllPaths has been tested and works correctly
        MainController.mainController= new MainController();
        HelloController.helloController= new HelloController();
        Landmark start = new Landmark("Louvre", 0.0, 0.0, 10,false);
        Landmark end = new Landmark("Notre Dame", 1.0, 1.0, 10,false);
        parisMap.addLandmark(start);
        parisMap.addLandmark(end);
        MainController.mainController.landmarks.add(start);
        MainController.mainController.landmarks.add(end);
        Street street = new Street("Louvre-Notre Dame", start, end);
        parisMap.addStreet(street);

        List<List<String>> allPaths = parisMap.findAllPaths("Louvre", "Notre Dame");
        int requestedPaths = 1;
        List<List<String>> randomPaths = parisMap.getRandomPaths(allPaths, requestedPaths);
        Assertions.assertEquals(requestedPaths, randomPaths.size());
    }
}
