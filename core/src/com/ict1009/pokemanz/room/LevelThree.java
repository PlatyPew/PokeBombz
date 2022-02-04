package com.ict1009.pokemanz.room;

import com.badlogic.gdx.utils.Array;
import com.ict1009.pokemanz.entity.Player;

public class LevelThree extends Map {
    final private static String textureLocation = "room/background.png";

    // Coordinates of unbreakable obstacles
    final private static int[][] unbreakable = {
        {1, 1},  {2, 1},  {3, 1},  {4, 1},  {6, 1},  {7, 1},  {8, 1},  {9, 1},  {11, 1},
        {12, 1}, {13, 1}, {14, 1}, {1, 2},  {14, 2}, {3, 3},  {4, 3},  {6, 3},  {9, 3},
        {11, 3}, {12, 3}, {1, 4},  {3, 4},  {6, 4},  {9, 4},  {12, 4}, {14, 4}, {1, 5},
        {3, 5},  {6, 5},  {9, 5},  {12, 5}, {14, 5}, {3, 6},  {4, 6},  {6, 6},  {9, 6},
        {11, 6}, {12, 6}, {1, 7},  {14, 7}, {1, 8},  {2, 8},  {3, 8},  {4, 8},  {6, 8},
        {7, 8},  {8, 8},  {9, 8},  {11, 8}, {12, 8}, {13, 8}, {14, 8}
    };

    // Coordinates of breakable obstacles
    final private static int[][] breakable = {
        {3, 0},  {4, 0},  {5, 0},  {6, 0},  {7, 0},  {8, 0},  {9, 0},  {10, 0}, {11, 0}, {12, 0},
        {5, 1},  {10, 1}, {5, 2},  {10, 2}, {0, 3},  {1, 3},  {2, 3},  {3, 3},  {4, 3},  {5, 3},
        {6, 3},  {7, 3},  {8, 3},  {9, 3},  {10, 3}, {11, 3}, {12, 3}, {13, 3}, {14, 3}, {15, 3},
        {0, 4},  {5, 4},  {10, 4}, {15, 4}, {0, 5},  {5, 5},  {10, 5}, {15, 5}, {0, 6},  {1, 6},
        {2, 6},  {3, 6},  {4, 6},  {5, 6},  {6, 6},  {7, 6},  {8, 6},  {9, 6},  {10, 6}, {11, 6},
        {12, 6}, {13, 6}, {14, 6}, {15, 6}, {5, 7},  {10, 7}, {5, 8},  {10, 8}, {3, 9},  {4, 9},
        {5, 9},  {6, 9},  {7, 9},  {8, 9},  {9, 9},  {10, 9}, {11, 9}, {12, 9},
    };

    public LevelThree(Array<Player> players) {
        super(players, textureLocation, unbreakable, breakable);
    }
}
