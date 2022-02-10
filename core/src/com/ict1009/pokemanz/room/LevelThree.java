package com.ict1009.pokemanz.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class LevelThree extends Map {
    final private static String textureLocation = "room/background.png";

    private Music gameMusic;

    // Coordinates of unbreakable obstacles
    final private static int[][] unbreakable = {
        {1, 1},  {1, 2},  {1, 4},  {1, 5},  {1, 7},  {1, 8},  {2, 1},  {2, 8},  {3, 1},
        {3, 3},  {3, 4},  {3, 5},  {3, 6},  {3, 8},  {4, 1},  {4, 3},  {4, 6},  {4, 8},
        {6, 1},  {6, 3},  {6, 4},  {6, 5},  {6, 6},  {6, 8},  {7, 1},  {7, 8},  {8, 1},
        {8, 8},  {9, 1},  {9, 3},  {9, 4},  {9, 5},  {9, 6},  {9, 8},  {11, 1}, {11, 3},
        {11, 6}, {11, 8}, {12, 1}, {12, 3}, {12, 4}, {12, 5}, {12, 6}, {12, 8}, {13, 1},
        {13, 8}, {14, 1}, {14, 2}, {14, 4}, {14, 5}, {14, 7}, {14, 8}};

    // Coordinates of breakable obstacles
    final private static int[][] breakable = {
        {0, 3},  {0, 4},  {0, 5},  {0, 6},  {1, 3},  {1, 6},  {2, 3},  {2, 6},  {3, 0},  {3, 3},
        {3, 6},  {3, 9},  {4, 0},  {4, 3},  {4, 6},  {4, 9},  {5, 0},  {5, 1},  {5, 2},  {5, 3},
        {5, 4},  {5, 5},  {5, 6},  {5, 7},  {5, 8},  {5, 9},  {6, 0},  {6, 3},  {6, 6},  {6, 9},
        {7, 0},  {7, 3},  {7, 6},  {7, 9},  {8, 0},  {8, 3},  {8, 6},  {8, 9},  {9, 0},  {9, 3},
        {9, 6},  {9, 9},  {10, 0}, {10, 1}, {10, 2}, {10, 3}, {10, 4}, {10, 5}, {10, 6}, {10, 7},
        {10, 8}, {10, 9}, {11, 0}, {11, 3}, {11, 6}, {11, 9}, {12, 0}, {12, 3}, {12, 6}, {12, 9},
        {13, 3}, {13, 6}, {14, 3}, {14, 6}, {15, 3}, {15, 4}, {15, 5}, {15, 6},
    };

    public LevelThree() {
        super(textureLocation, unbreakable, breakable);
    }
    @Override
    public void setGameMusic(){
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/music_game6.ogg"));
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    public Music getGameMusic(){
        return gameMusic;
    }
}
