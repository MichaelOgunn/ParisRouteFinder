package com.example.parisroutefinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParisMap {
    // Using an adjacency list representation
    Map<Landmark, List<Street>> map;

    public ParisMap() {
        this.map = new HashMap<>();
    }

    // Methods to add/remove landmarks and streets, find routes, etc.
}