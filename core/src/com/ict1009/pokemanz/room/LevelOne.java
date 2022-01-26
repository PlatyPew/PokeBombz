package com.ict1009.pokemanz.room;

public class LevelOne extends Map {
    final private static String textureLocation = "room/background.png";

    // Coordinates of unbreakable obstacles
    final private static int[][] unbreakable = {
        {1, 1}, {2, 1}, {4, 1}, {5, 1}, {7, 1}, {9, 1}, {11, 1}, {13, 1}, {14, 1},
        {1, 2}, {2, 2}, {4, 2}, {5, 2}, {7, 2}, {9, 2}, {11, 2}, {13, 2}, {14, 2},
        {1, 4}, {2, 4}, {4, 4}, {5, 4}, {7, 4}, {9, 4}, {11, 4}, {13, 4}, {14, 4},
        {1, 5}, {2, 5}, {4, 5}, {5, 5}, {7, 5}, {9, 5}, {11, 5}, {13, 5}, {14, 5},
        {1, 7}, {2, 7}, {4, 7}, {5, 7}, {7, 7}, {9, 7}, {11, 7}, {13, 7}, {14, 7},
        {1, 8}, {2, 8}, {4, 8}, {5, 8}, {7, 8}, {9, 8}, {11, 8}, {13, 8}, {14, 8},
    };

    // Coordinates of breakable obstacles
    final private static int[][] breakable = {{1, 3}};

    public LevelOne() {
        super(textureLocation, unbreakable, breakable);
    }
}
