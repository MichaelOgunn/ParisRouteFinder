//package com.example.parisroutefinder;
//
//import javafx.scene.image.WritableImage;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ParisMapTest {
//
//    private ParisMap parisMap;
//
//    @BeforeEach
//    void setUp() {
//        parisMap = new ParisMap(10, new WritableImage(100, 100)); // Example size
//    }
//
//    @Test
//    void addLandmark() {
//        Landmark landmark = new Landmark("Eiffel Tower", 0, 0, 10,true);
//        parisMap.addLandmark(landmark);
//
//        // Assert that the landmark was added
//        assertTrue(parisMap.getLandmarks().containsKey("Eiffel Tower"));
//    }
//
//    @Test
//    void removeLandmark() {
//        Landmark landmark = new Landmark("Eiffel Tower", 0, 0, 10);
//        parisMap.addLandmark(landmark);
//
//        // Ensure landmark was added
//        assertTrue(parisMap.getLandmarks().containsKey("Eiffel Tower"));
//
//        parisMap.removeLandmark(landmark);
//
//        // Assert that the landmark was removed
//        assertFalse(parisMap.getLandmarks().containsKey("Eiffel Tower"));
//    }
//
//    @Test
//    void addStreet() {
//        Landmark startLandmark = new Landmark("Eiffel Tower", 0, 0, 10);
//        Landmark endLandmark = new Landmark("Louvre", 1, 1, 10);
//        parisMap.addLandmark(startLandmark);
//        parisMap.addLandmark(endLandmark);
//
//        Street street = new Street("Eiffel to Louvre", startLandmark, endLandmark, 1.0);
//        parisMap.addStreet(street);
//
//        // Assert that the street was added
//        int startLandmarkIndex = parisMap.getLandmarks().get(startLandmark.getName());
//        int endLandmarkIndex = parisMap.getLandmarks().get(endLandmark.getName());
//        assertEquals(1.0, parisMap.getAdjacencyMatrix()[startLandmarkIndex][endLandmarkIndex]);
//        assertEquals(1.0, parisMap.getAdjacencyMatrix()[endLandmarkIndex][startLandmarkIndex]);
//    }
//}