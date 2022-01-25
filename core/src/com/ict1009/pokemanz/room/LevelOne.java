package com.ict1009.pokemanz.room;

public class LevelOne extends Map {
    final private static String textureLocation = "room/background.png";
    final private static int[][] obstaclesUnbreakable = {{1, 1},  {1, 10}, {2, 5}, {2, 7},  {2, 6},
                                                  {3, 15}, {3, 10}, {3, 5}, {5, 6},  {7, 10},
                                                  {9, 1},  {5, 10}, {7, 1}, {16, 10}};

    public LevelOne() {
        super(textureLocation, obstaclesUnbreakable);
    }
}
