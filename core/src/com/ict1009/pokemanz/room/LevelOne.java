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
    final private static int[][] breakable = {
            {1, 3}, {3, 0},  {4, 0},  {5, 0},  {6, 0},  {7, 0},  {8, 0},  {9, 0},  {10, 0}, {11, 0}, {12, 0},
            {5, 1}, {10, 1}, {5, 2},  {10, 2}, {0, 3},  {1, 3},  {2, 3},  {3, 3},  {4, 3},  {5, 3},
            {6, 3}, {7, 3},  {8, 3},  {9, 3},  {10, 3}, {11, 3}, {12, 3}, {13, 3}, {14, 3}, {15, 3},
            {0, 4}, {5, 4},  {10, 4}, {15, 4}, {0, 5},  {5, 5},  {10, 5}, {15, 5}, {0, 6},  {1, 6},
            {2, 6}, {3, 6},  {4, 6},  {5, 6},  {6, 6},  {7, 6},  {8, 6},  {9, 6},  {10, 6}, {11, 6},
            {12, 6}, {13, 6}, {14, 6}, {15, 6}, {5, 7},  {10, 7}, {5, 8},  {10, 8}, {3, 9},  {4, 9},
            {5, 9}, {6, 9},  {7, 9},  {8, 9},  {9, 9},  {10, 9}, {11, 9}, {12, 9}, {0, 2}, {0, 7}, {2, 9},
            {2, 0}, {3, 1}, {3, 2}, {3, 4}, {3, 5}, {3, 7}, {3, 8},
            {6, 1}, {6, 2}, {6, 4}, {6, 5}, {6, 7}, {6, 8},
            {8, 1}, {8, 2}, {8, 4}, {8, 5}, {8, 7}, {8, 8},
            {12, 1}, {12, 2}, {12, 4}, {12, 5}, {12, 7}, {12, 8},
            {13, 0}, {13, 9}, {15, 2}, {15, 7},
    };

    public LevelOne() {
        super(textureLocation, unbreakable, breakable);
    }
}
