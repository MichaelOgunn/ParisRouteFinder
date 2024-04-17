package com.example.parisroutefinder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetTest {
    private Street street;
    private Landmark startLandmark;
    private Landmark endLandmark;

    @BeforeEach
    void setUp() {
        startLandmark = new Landmark("Eiffel Tower",48.85,2.29,10);
        endLandmark = new Landmark("Louvre Museum",48.86,2.33,10);
//        street = new Street("Eiffel-Louvre",3.5,startLandmark,endLandmark);
        street = new Street("Eiffel-Louvre",startLandmark,endLandmark);
    }


    @Test
    void getName() {
        assertEquals("Eiffel-Louvre",street.getName());
    }

    @Test
    void setName() {
        street.setName("Eiffel-Paris");
        assertEquals("Eiffel-Paris",street.getName());
    }

//    @Test
//    void getDistance() {
//        assertEquals(3,street.getDistance());
//    }

    @Test
    void setDistance() {
        street.setDistance(4.5);
        assertEquals(4.5,street.getDistance());
    }

    @Test
    void getStartLandmark() {
        assertSame(startLandmark,street.getStartLandmark());
    }

    @Test
    void setStartLandmark() {
        Landmark newLandmark = new Landmark("Notre Dame",48.85,2.29,10);
        street.setStartLandmark(newLandmark);
        assertSame(newLandmark,street.getStartLandmark());
    }

    @Test
    void getEndLandmark() {
        assertSame(endLandmark,street.getEndLandmark());
    }

    @Test
    void setEndLandmark() {
        Landmark newEnd = new Landmark("Panthéon", 48.8463, 2.3461, 8);
        street.setEndLandmark(newEnd);
        assertSame(newEnd, street.getEndLandmark(), "The end landmark should be updated to Panthéon.");
    }
}